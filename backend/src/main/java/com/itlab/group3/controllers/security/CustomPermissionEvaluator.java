package com.itlab.group3.controllers.security;

import com.itlab.group3.dao.model.*;
import com.itlab.group3.services.UserAssignmentsService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomPermissionEvaluator implements PermissionEvaluator {

    private static final String READ = "READ";
    private static final String UPDATE = "UPDATE";
    private static final String DELETE = "DELETE";

    @NonNull
    private final UserFactory userFactory;

    @NonNull
    private final UserAssignmentsService userAssignmentsService;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if ((authentication == null) || (targetDomainObject == null) || !(permission instanceof String)) {
            return false;
        }
        if (authentication.getPrincipal().toString().equals("anonymousUser")) {
            return anonymousPrivilege(targetDomainObject.getClass().getSimpleName(), permission.toString().toUpperCase());
        }
        final UserAgent userAgent = userFactory.getInstance(authentication);
        return hasPrivilege(userAgent, targetDomainObject, permission.toString().toUpperCase());
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }

    private boolean anonymousPrivilege(String targetType, String permission) {
        return false;
    }

    private boolean hasPrivilege(UserAgent userAgent, Object target, String permission) {
        String targetType = target.getClass().getSimpleName();
        if (userAgent.isAdmin() && permission.equals(READ))
            return true;
        switch (targetType) {
            case "DetailedTask": {
                return detailedTaskPermission(userAgent, (DetailedTask) target, permission);
            }
            case "Feature": {
                return featurePermission(userAgent, (Feature) target, permission);
            }
            case "Location": {
                return locationPermission(userAgent, permission);
            }
            case "Project": {
                return projectPermission(userAgent, (Project) target, permission);
            }
            case "ReportDetail": {
                return false;
            }
            case "Report": {
                return reportPermission(userAgent, (Report) target, permission);
            }
            case "ReportRecord": {
                return reportRecordPermission(userAgent, (ReportRecord) target, permission);
            }
            case "Task": {
                return taskPermission(userAgent, (Task) target, permission);
            }
            case "User": {
                return userPermission(userAgent, (User) target, permission);
            }
            default: {
                break;
            }

        }
        return false;
    }

    private boolean hasPermission(UserAgent userAgent, About target, String permission) {
        return userAgent.isAdmin() || userAgent.isUser();
    }

    private boolean reportPermission(UserAgent userAgent, Report report, String permission) {
        boolean result = userAgent.isAdmin();
        if (!result || userAgent.isUser() && userAgent.getUser().isPresent()) {
            User user = userAgent.getUser().get();
            result |= (permission.equals(READ) || permission.equals(UPDATE) || permission.equals(DELETE))
                    && user.getId().equals(report.getUser().getId());
        }
        return result;
    }

    private boolean reportRecordPermission(UserAgent userAgent, ReportRecord reportRecord, String permission) {
        boolean result = userAgent.isAdmin();
        if (!result || userAgent.isUser() && userAgent.getUser().isPresent()) {
            User user = userAgent.getUser().get();
            result |= (permission.equals(READ) || permission.equals(UPDATE) || permission.equals(DELETE))
                    && user.getId().equals(reportRecord.getUser().getId());
        }
        return result;
    }

    private boolean userPermission(UserAgent userAgent, User targetObject, String permission) {
        boolean result = userAgent.isAdmin();

        if (!result || userAgent.isUser() && userAgent.getUser().isPresent()) {
            result |= (permission.equals(READ) || permission.equals(UPDATE)) && userAgent.getUser().get().getId().equals(targetObject.getId());
        }
        return result;
    }

    private boolean projectPermission(UserAgent userAgent, Project project, String permission) {
        boolean result = userAgent.isAdmin();

        if (!result || userAgent.isUser() && userAgent.getUser().isPresent()) {
            User user = userAgent.getUser().get();
            Optional<UserAssignments> userAssignments = userAssignmentsService.findByUser(user);
            result |= userAssignments.isPresent() && (permission.equals(READ) && userAssignments.get().getProjects().contains(project));
        }
        return result;
    }

    private boolean featurePermission(UserAgent userAgent, Feature feature, String permission) {
        return projectPermission(userAgent, feature.getProject(), permission);
    }

    private boolean taskPermission(UserAgent userAgent, Task task, String permission) {
        return featurePermission(userAgent, task.getFeature(), permission);
    }

    private boolean detailedTaskPermission(UserAgent userAgent, DetailedTask detailedTask, String permission) {
        return taskPermission(userAgent, detailedTask.getTask(), permission);
    }


    private boolean locationPermission(UserAgent userAgent, String permission) {
        return userAgent.isAdmin() || permission.equals(READ);
    }
}
