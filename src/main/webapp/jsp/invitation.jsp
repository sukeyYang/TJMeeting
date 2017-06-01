<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2017/5/30
  Time: 13:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>邀请函-第七届泗宗同门会</title>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport"
        content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
  <link rel="stylesheet" type="text/css"
        href="http://cache.shchengdian.com/css/zv_index.css">
  <link rel="stylesheet" type="text/css"
        href="http://cache.shchengdian.com/css/iSlider.css">
  <link rel="stylesheet" type="text/css"
         href="http://res.wx.qq.com/open/libs/weui/1.1.2/weui.min.css">
  <style>
    .container {
      width: 100%;
      height: 100%;
      overflow: hidden;
    }

    .invitation {
      width: 6.4rem;
      height: 9.9rem;
      background: url("../images/invitation.jpg") no-repeat center center/cover;

    }

    .time-address {
      position: absolute;
      width: 2.92rem;
      height: 0.78rem;
      background: url("../images/time&address.png") no-repeat center center/cover;
      left: .32rem;
      bottom: .76rem;

    }

    .page-2 {
      background: url("../images/theme-bg.jpg") no-repeat 0 0/100% 100%;
    }

    .theme-title {
      position: absolute;
      width: 2.42rem;
      height: 4.2rem;
      background: url("../images/theme-title.png") no-repeat center center/cover;
      left: 1.54rem;
      top: .76rem;

    }

    .page-2 .logo {
      position: absolute;
      width: 1.5rem;
      height: 0.926rem;
      background: url("../images/logo.jpg") no-repeat center center/cover;

      top: 1.2rem;
      right: .6rem;

    }

    .page-2 .theme-thumb-1 {
      position: absolute;
      width: 1.84rem;
      height: 3.38rem;
      background: url("../images/theme-thumb-2.png") no-repeat center center/cover;
      left: -0.01rem;
      bottom: 0;
    }

    .page-2 .theme-thumb-2 {
      position: absolute;
      width: 1.7rem;
      height: 3.38rem;
      background: url("../images/theme-thumb-3.png") no-repeat center center/cover;
      left: 1.88rem;
      bottom: 0;
    }

    .page-2 .theme-thumb-3 {
      position: absolute;
      width: 4.2rem;
      height: 4.2rem;
      background: url("../images/theme-thumb-1.png") no-repeat center center/cover;
      left: 0;
      bottom: 1.26rem;
    }

    .page-2 .theme-thumb-4 {
      position: absolute;
      width: 2.1rem;
      height: 4.2rem;
      background: url("../images/theme-thumb-4.png") no-repeat center center/cover;
      right: 0;
      bottom: 1.26rem;
    }

    .timeline {
      position: absolute;
      width: 2.85rem;
      height: 7.9rem;
      background: url("../images/timeline.png") no-repeat center center/cover;
      left: 0.92rem;
      top: 1.2rem;
      z-index: 9;
    }

    .timeline-thumb-1 {
      position: absolute;
      width: 2.08rem;
      height: 2.08rem;
      background: url("../images/timeline-thumb-1.jpg") no-repeat center center/cover;
      right: -1.86rem;
      top: .22rem;
    }

    .timeline-thumb-2 {
      position: absolute;
      width: 2.08rem;
      height: 2.08rem;
      background: url("../images/timeline-thumb-2.png") no-repeat center center/cover;
      left: -.32rem;
      top: 1.64rem;
    }

    .timeline-thumb-3 {
      position: absolute;
      width: 2.08rem;
      height: 2.08rem;
      background: url("../images/timeline-thumb-3.jpg") no-repeat center center/cover;
      right: -1.86rem;
      top: 4rem;
    }

    .timeline-thumb-4 {
      position: absolute;
      width: 2.08rem;
      height: 2.08rem;
      background: url("../images/timeline-thumb-4.png") no-repeat center center/cover;
      left: -.1rem;
      bottom: 0.1rem;

    }

    .plan {
      text-align: center;
      padding: 0.32rem;
      font-weight: bold;
      color: #000;
      font-size: 0.3rem;
    }

    .schedule-1 {
      position: absolute;
      top: 0.16rem;
      left: 0.45rem;
      color: #000;
    }

    .schedule-2 {
      position: absolute;
      top: 2.55rem;
      right: -1.76rem;
      color: #000;
    }

    .schedule-3 {
      position: absolute;
      top: 4.16rem;
      left: 0.72rem;
      color: #000;
    }

    .schedule-4 {
      position: absolute;
      bottom: .64rem;
      right: -1.76rem;
      color: #000;
    }

    .time {
      background-color: #be0000;
      font-size: .2rem;
      color: #fff;
      text-align: center;
      font-weight: 600;
      width: 1.58rem;
    }

    .schedule-info {
      font-size: 0.18rem;
      padding: 0.16rem 0 0 0.12rem;
      line-height: 0.32rem;

    }

    .page-4 {
      background: #f8f8f8;
      color:#000;
      overflow: scroll !important;
    }

    .page__hd {
      padding-top:0.3rem;
      text-align: center;
    }

    .btn-space {
      padding: 20px 15px;
    }


  </style>

</head>
<body>

<div class="wrap" id="wrap">
  <div class="item container">
    <div class="invitation"></div>
    <div class="time-address"></div>
  </div>

  <div class="item container page-2">
    <div class="theme-title"></div>
    <div class="logo"></div>
    <div class="theme-thumb-1"></div>
    <div class="theme-thumb-2"></div>
    <div class="theme-thumb-3"></div>
    <div class="theme-thumb-4"></div>
  </div>

  <div class="item container">
    <div class="plan">日程安排</div>
    <div class="timeline">
      <div class="schedule-1">
        <p class="time">7月14日(周五)</p>
        <p class="schedule-info">16点前报道<br> 晚上举行欢迎文艺晚会</p>
      </div>
      <div class="timeline-thumb-1"></div>
      <div class="schedule-2">
        <p class="time">7月15日(周六)</p>
        <p class="schedule-info">上午举行论坛<br>下午向青南贵德进发</p>
      </div>
      <div class="timeline-thumb-2"></div>
      <div class="schedule-3">
        <p class="time">7月15日(周六)</p>
        <p class="schedule-info">上午举行论坛<br>下午向青南贵德进发</p>
      </div>
      <div class="timeline-thumb-3"></div>
      <div class="schedule-4">
        <p class="time">7月17日(周一)</p>
        <p class="schedule-info">欢送同门会</p>
      </div>
      <div class="timeline-thumb-4"></div>
    </div>
  </div>

  <div class="item container page-4">
    <div class="page__hd">
      <h3 class="page__title">请填写参会信息</h3>
    </div>
    <div class="page__bd">
      <form>
        <div class="weui-cells__title">参会人信息</div>
        <div class="weui-cells weui-cells_form">
          <div class="weui-cell">
            <div class="weui-cell__hd">
              <label class="weui-label" for="a11">参会人姓名</label>
            </div>
            <div class="weui-cell__bd">
              <input class="weui-input" type="text" placeholder="请输入姓名"
                 name="name"    id="a11"/>
            </div>
          </div>
          <div class="weui-cell">
            <div class="weui-cell__hd">
              <label class="weui-label" for="a12">联系电话</label>
            </div>
            <div class="weui-cell__bd">
              <input class="weui-input" type="number" pattern="[0-9]*"
                     placeholder="请输入手机号码" id="a12"/>
            </div>
          </div>
          <div class="weui-cell">
            <div class="weui-cell__hd">
              <label class="weui-label" for="a13">参会人数</label>
            </div>
            <div class="weui-cell__bd">
              <input class="weui-input" type="number" pattern="[0-9]*"
                     placeholder="请输入参会人数" id="a13"/>
            </div>
          </div>
        </div>

        <div class="weui-cells__title">事务联系人（与参加人不一致时填写）</div>
        <div class="weui-cells weui-cells_form">
          <div class="weui-cell">
            <div class="weui-cell__hd">
              <label class="weui-label" for="b11">联系人姓名</label>
            </div>
            <div class="weui-cell__bd">
              <input class="weui-input" type="text" placeholder="请输入姓名"
                     id="b11"/>
            </div>
          </div>
          <div class="weui-cell">
            <div class="weui-cell__hd">
              <label class="weui-label" for="b12">联系人电话</label>
            </div>
            <div class="weui-cell__bd">
              <input class="weui-input" type="number" pattern="[0-9]*"
                     placeholder="请输入手机号码" id="b12"/>
            </div>
          </div>
        </div>

        <div class="weui-cells__title">参会人其它信息</div>
        <div class="weui-cells weui-cells_form">

          <div class="weui-cell weui-cell_switch">
            <div class="weui-cell__bd">是否入驻承办机构提供的酒店</div>
            <div class="weui-cell__ft">
              <input class="weui-switch" type="checkbox"/>
            </div>
          </div>
          <div class="weui-cell">
            <div class="weui-cell__hd">
              <label class="weui-label" for="c13">入住时间</label>
            </div>
            <div class="weui-cell__bd">
              <input class="weui-input" type="date" value="" placeholder=""
                     id="c13"/>
            </div>
          </div>
          <div class="weui-cell">
            <div class="weui-cell__hd">
              <label class="weui-label" for="c14">退房日期</label>
            </div>
            <div class="weui-cell__bd">
              <input class="weui-input" type="date" value="" placeholder=""
                     id="c14"/>
            </div>
          </div>
        </div>

        <div class="weui-cells__title">参与活动</div>
        <div class="weui-cells weui-cells_checkbox">
          <label class="weui-cell weui-check__label" for="d11">
            <div class="weui-cell__hd">
              <input type="checkbox" class="weui-check" name="checkbox1"
                     id="d11" checked="checked"/>
              <i class="weui-icon-checked"></i>
            </div>
            <div class="weui-cell__bd">
              <p>7月14日晚上 欢迎晚会</p>
            </div>
          </label>
          <label class="weui-cell weui-check__label" for="d12">
            <div class="weui-cell__hd">
              <input type="checkbox" class="weui-check" name="checkbox1"
                     id="d12" checked="checked"/>
              <i class="weui-icon-checked"></i>
            </div>
            <div class="weui-cell__bd">
              <p>7月15日上午 论坛</p>
            </div>
          </label>
          <label class="weui-cell weui-check__label" for="d13">
            <div class="weui-cell__hd">
              <input type="checkbox" class="weui-check" name="checkbox1"
                     id="d13" checked="checked"/>
              <i class="weui-icon-checked"></i>
            </div>
            <div class="weui-cell__bd">
              <p>7月15日下午 青南贵德</p>
            </div>
          </label>
          <label class="weui-cell weui-check__label" for="d14">
            <div class="weui-cell__hd">
              <input type="checkbox" class="weui-check" name="checkbox1"
                     id="d14" checked="checked"/>
              <i class="weui-icon-checked"></i>
            </div>
            <div class="weui-cell__bd">
              <p>7月16日白天 青海湖、沙岛、原子城、金沙滩</p>
            </div>
          </label>
          <label class="weui-cell weui-check__label" for="d15">
            <div class="weui-cell__hd">
              <input type="checkbox" class="weui-check" name="checkbox1"
                     id="d15" checked="checked"/>
              <i class="weui-icon-checked"></i>
            </div>
            <div class="weui-cell__bd">
              <p>7月16日晚上 篝火晚会</p>
            </div>
          </label>
        </div>


        <div class="weui-cells__title">自荐表演节目信息</div>
        <div class="weui-cells weui-cells_form">
          <div class="weui-cell">
            <div class="weui-cell__hd">
              <label class="weui-label" for="e11">节目类型</label>
            </div>
            <div class="weui-cell__bd">
              <input class="weui-input" type="text" placeholder="请输入节目类型"
                     id="e11"/>
            </div>
          </div>
          <div class="weui-cell">
            <div class="weui-cell__hd">
              <label class="weui-label" for="e12">节目名称</label>
            </div>
            <div class="weui-cell__bd">
              <input class="weui-input" type="text" placeholder="请输入节目名称"
                     id="e12"/>
            </div>
          </div>
          <div class="weui-cell">
            <div class="weui-cell__hd">
              <label class="weui-label" for="e13">演员姓名</label>
            </div>
            <div class="weui-cell__bd">
              <input class="weui-input" type="text" placeholder="如有多个演员请使用逗号分隔"
                     id="e13"/>
            </div>
          </div>
        </div>

        <div class="weui-cells__title">节目表演要求</div>
        <div class="weui-cells weui-cells_form">
          <div class="weui-cell">
            <div class="weui-cell__bd">
            <textarea class="weui-textarea" placeholder="例如乐器、道具"
                      rows="3"></textarea>
              <div class="weui-textarea-counter"><span>0</span>/200</div>
            </div>
          </div>
        </div>

        <div class="btn-space">
          <a href="javascript:;" class="weui-btn weui-btn_primary">提&nbsp;&nbsp;&nbsp;&nbsp;交</a>
        </div>
      </form>
    </div>
  </div>

</div>
<!-- 加载提示 _S -->

<div class="slider">
  <span class="sprite_global"></span>
</div>

<script
  src="http://cache.shchengdian.com/js/PhotoClip-V2.05/jquery-3.1.1.min.js"></script>
<script type="text/javascript"
        src="http://cache.shchengdian.com/iSlider.js"></script>
<script>
  $(function () {
    document.documentElement.style.fontSize = document.documentElement.clientWidth / 6.4 + 'px';
    $(window).resize(function () {
      document.documentElement.style.fontSize = document.documentElement.clientWidth / 6.4 + 'px';
    });
    $("body").on("touchmove", function (event) {
      event.preventDefault;
    }, false);
  });
  var myslider = new iSlider({
    wrap: '#wrap',
    item: '.item',
    playClass: 'play',
    onslide: function (index) {

      console.info(index)
    }
  });

</script>
</body>
</html>
