(function () {

    var date = {
        //格式化时间
        fmt: function (de, fmt) {
            var date = new Date(typeof de == "string" ? de.replace(/-/g, "/") : de);
            if (!fmt) {
                fmt = "yyyy-mm-dd";
            }
            var o = {
                "M+": date.getMonth() + 1, //月份
                "d+": date.getDate(), //日
                "h+": date.getHours(), //小时
                "m+": date.getMinutes(), //分
                "s+": date.getSeconds(), //秒
                "q+": Math.floor((date.getMonth() + 3) / 3), //季度
                "S": date.getMilliseconds() //毫秒
            };
            if (/(y+)/.test(fmt))
                fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
            for (var k in o)
                if (new RegExp("(" + k + ")").test(fmt))
                    fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
            return fmt;
        },
        diyFmt: function (time) {
            //如果传进来的是字符串，转成时间
            if (Object.prototype.toString.call(new Date()) != Object.prototype.toString.call(time)) {
                time = new Date(time);
            }
            var nowDate = new Date().getTime();
            var dif = (nowDate - time) / 1000;
            //时间字符串
            var timestr = "";
            if (dif < 60) {
                timestr = "刚刚";
            } else if (dif < 3600) {
                timestr = moment(time).format('A') + moment(time).format('H:mm');
            } else if (dif < 86400) {
                timestr = "昨天";
            } else if (dif < 172800) {
                timestr = moment(time).format("dddd");
            } else if (dif < 31536000) {
                timestr = moment(time).format("MMM Do").replace(" ", "");
            } else {
                timestr = moment(time).subtract(10, 'days').calendar();
            }
            return timestr;
        }
    }

    var util = {

        date: date,

    }

    if (typeof wg != "object") {
        window.wg = {};
    }

    window.wg.util=util;

}())