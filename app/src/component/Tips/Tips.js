import React from "react";
import { Carousel } from "antd";
import '../../style/tips.scss';
import tips from "../../const/tips";

const Tips = props => {
  function getRandom(arr, count) {
    var shuffled = arr.slice(0),
      i = arr.length,
      min = i - count,
      temp,
      index;
    while (i-- > min) {
      index = Math.floor((i + 1) * Math.random());
      temp = shuffled[index];
      shuffled[index] = shuffled[i];
      shuffled[i] = temp;
    }
    return shuffled.slice(min);
  }
  var list = props.tips ? props.tips : getRandom(tips, 5);

  return (
    <div className={'container' + " " + props.className}>
      <Carousel autoplay>
        {list.map((item, i) => {
          return (
            <p className='tip' key={i}>
              {item}
            </p>
          );
        })}
      </Carousel>
    </div>
  );
};
export default Tips;
