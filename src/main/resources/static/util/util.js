// 验证手机, 带3-4位区号-电话-分机号
function regPhone(phone) {
    var reg = /((^1[3|4|5|7|8]\d{9}$)|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)/;
    var boo = reg.test(phone)
    return boo;
}

// 验证姓名
function regName(name) {
    var reg = /^(?:[\u4e00-\u9fa5]+)(?:·[\u4e00-\u9fa5]+)*$|^[a-zA-Z0-9]+\s?[\.·\-()a-zA-Z]*[a-zA-Z]+$/;
    var boo = reg.test(name);

    // console.log(boo)
    return boo;
}

//判断字符是否为空
function isEmpty(obj) {
    return (typeof obj === 'undefined' || obj === null || obj === "");
}