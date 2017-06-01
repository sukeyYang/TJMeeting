var time_stamp = "";
var nonce_Str = "";
var sign = "";
var url = window.location.href;
var flag = 0;
$.ajax({
  url: "http://www.cnwup.com/survey/wechat/getWeiXinJsConf",
  type: "POST",
  async: false,
  data: {
    "url": url
  },
  success: function (result) {
    var data = JSON.parse(result);
    time_stamp = data.timeStamp;
    nonce_Str = data.nonceStr;
    sign = data.signature;
  }
});

wx.config({
  debug: false,
  appId: "wxd210fa2c202d0ea0",
  timestamp: time_stamp,
  nonceStr: nonce_Str,
  signature: sign,
  jsApiList: ['onMenuShareTimeline', 'onMenuShareAppMessage']
// 功能列表，我们要使用JS-SDK的什么功能
});

// config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在 页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready 函数中。
wx.ready(function () {

  // 获取“分享到朋友圈”按钮点击状态及自定义分享内容接口
  wx.onMenuShareTimeline({
    title: Title + "——" + Desc, // 分享标题
    link: "http://www.cnwup.com/survey/questionnaire",
    imgUrl: imageUrl, // 分享图标
    success: function () {
      var step=$("#part").val();
      if(setp=='9' && flag==0){
        sentredpack();
        flag +=1;
      }

    },
    cancel: function () {

    }
  });
  // 获取“分享给朋友”按钮点击状态及自定义分享内容接口

  wx.onMenuShareAppMessage({
    title: Title, // 分享标题
    desc: Desc, // 分享描述
    link: "http://www.cnwup.com/survey/questionnaire",
    imgUrl: imageUrl, // 分享图标
    type: 'link', // 分享类型,music、video或link，不填默认为link
    success: function () {
      var step=$("#part").val();
      if(setp=='9' && flag==0){
        sentredpack();
        flag +=1;
      }
    },
    cancel: function () {

    }
  });
});

function sentredpack() {
  $.ajax({
    url: "redpack",
    type: "POST",
    data: {"data": "null"},
    success: function (result) {
      alert(result);
    }
  })
}

