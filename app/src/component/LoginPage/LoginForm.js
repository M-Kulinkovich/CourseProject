import React from "react";
import "../../style/LoginForm.scss";
import LoginPageContainer from "../LoginPage/LoginPageContainer";
import Tips from "../../component/Tips/Tips";



class Login extends React.Component {
  render(){
  return (
    <div className='container'>        
            <h2 className='header'>
              Добро пожаловать на работу!
              
            </h2>
          <div className='buttons'>        
      </div>
      <div className='main'>
      <p className='pageName'>
          Вход
          <span> / Login</span>
        </p>
        <LoginPageContainer />
        <div>@MASM-4</div>
      </div>
    </div>
  );
};
}
export default (Login);