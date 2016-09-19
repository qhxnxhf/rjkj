/// <reference path="jquery-1.8.2.js" />
/// <reference path="xg.js" />

function contentList(containerId, categoryID, keyword, pagerContainerId, pageSize, pagerSize) {
    XG.List.init(containerId, "web/newsPage", { "lmid": categoryID, "keyword": keyword }, "ul", null, function (row) {
    	var $li = $("<li/>");
    	if(isNew(row.pubDate)){$("<em/>").addClass("icon-new").appendTo($li);}    	
        $("<i/>").addClass("icon-list left").appendTo($li);
        $("<span/>").html(row.pubDate.substr(2)).addClass("right").appendTo($li);
        $("<a/>").html(row.newsTitle).attr("target", "_blank").attr("href", "web/showNews/" + row.id).appendTo($li);
        return $li;
    }, pagerContainerId, pageSize, pagerSize);
}
function isNew(dateStr){
    var date=new Date(dateStr);
    var now=new Date();
    return parseInt(Math.abs(now-date)/1000/60/60/24)<=3;
}

$(function () {
    XG.FadeLoop.init("fade", 1000, 5000);
   // XG.Focus.init("focus");
   // XG.Scroll.init("ad", "ul", 1, 1, "up", 1000, 3000);
   // XG.Scroll.init("show", "ul", 1, 1, "left", 1000, 2000);
    //XG.Editor.init("editor");
    //var login = XG.Dialog.init("login", "用户登录");
    //$("#login-button").click(function () { login.open(); });

    var setting = {};
    var zNodes = [
        { id: 1, parentId: 0, name: "名称1" },
        { id: 11, parentId: 1, name: "名称11" },
        { id: 2, parentId: 1, name: "名称2" },
        { id: 21, parentId: 2, name: "名称21" },
        { id: 22, parentId: 2, name: "名称22" }
    ];
    if ($.fn.zTree) {
        $.fn.zTree.init($("#ztree"), setting, zNodes);
    }

    $(".nav-top ul li.nav").hover(function () {
        $(this).find("div").css("display", "block");
    }, function () {
        $(this).find("div").css("display", "none");
    });
    $(".nav-top ul li.nav").click(function () {
        location.href = $(this).find("a").attr("href");
    });
    $(".nav-top ul li.nav div dl dd").hover(function () {
        $(this).addClass("hover");
        $(this).find("dl").css("display", "block");
    }, function () {
        $(this).removeClass("hover");
        $(this).find("dl").css("display", "none");
    });
    $(".nav-top ul li.nav div dl dt").hover(function () {
        $(this).addClass("hover");
    }, function () {
        $(this).removeClass("hover");
    });
    $(".nav-top ul li.nav div dl dd").click(function () {
        location.href = $(this).find("a").attr("href");
    });

    $("table.link td a").hover(function () {
        $(this).parent("td").prev("td").find("i").removeClass("icon-link").addClass("icon-link-hover");
    }, function () {
        $(this).parent("td").prev("td").find("i").removeClass("icon-link-hover").addClass("icon-link");
    });

    //$(".box").hover(function () {
    //    $(this).addClass("hover");
    //}, function () {
    //    $(this).removeClass("hover");
    //});
    $(".box ul li").hover(function () {
        $(this).find("i").removeClass("icon-list");
        $(this).find("i").addClass("icon-list-hover");
    }, function () {
        $(this).find("i").removeClass("icon-list-hover");
        $(this).find("i").addClass("icon-list");
    });

    $(".list ul li").hover(function () {
        $(this).find("i").removeClass("icon-list");
        $(this).find("i").addClass("icon-list-hover");
    }, function () {
        $(this).find("i").removeClass("icon-list-hover");
        $(this).find("i").addClass("icon-list");
    });

    $(document).keydown(function (e) { if (e.keyCode == 13) { login(); } });
    //$("#search").val("请输入关键字").blur(function () { if ($(this).val() == "") { $(this).val("请输入关键字"); } }).focus(function () { $(this).val(""); });
});
