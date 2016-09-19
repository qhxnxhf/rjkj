/// <reference path="jquery-1.8.2.js" />
/// <reference path="xg.js" />

function contentList(containerId, categoryID, keyword, pagerContainerId,showNews, pageSize, pagerSize) {
    XG.List.init(containerId, "web/newsPage", { "lmid": categoryID, "kwd": keyword }, "ul", null, function (row) {
        var $li = $("<li/>");
        $("<i/>").addClass("icon-list left").appendTo($li);
        $("<span/>").html(row.pubDate.substr(2)).addClass("right").appendTo($li);
        $("<a/>").html(isNew(row.pubDate) ? "<em>NEW</em>" + row.newsTitle : row.newsTitle).attr("href", showNews + row.id).attr("target", "_blank").appendTo($li);
        return $li;
    }, pagerContainerId, pageSize, pagerSize);
}
function isNew(dateStr) {
    var date = new Date(dateStr);
    var now = new Date();
    return parseInt(Math.abs(now - date) / 1000 / 60 / 60 / 24) > 3
}

//{id:"",title:"",category:[{id:"",title:"",contents:[{id:"",title:"",time:""},...,{}]},...,{}]}
function categoryTabInit(postURL, domID, categoryID, categoryURL, contentURL, categoryTabSize, contentSize) {
    $.post(postURL, { "categoryID": categoryID, "categoryTabSize": categoryTabSize, "contentSize": contentSize }, function (result) {
        var $dom = $("#" + domID).addClass("box");
        var html = '<h3><i class="icon-title-left"></i><span>' + result.title + '</span><i class="icon-title-right"></i></h3>';
        html += '<h6><a href="' + categoryURL + categoryID + '">MORE&nbsp;&gt;&gt;</a></h6>';
        $dom.html(html);
        var $dl = $("<dl/>").appendTo($dom);
        $.each(result.category, function (index, obj) {
            $("<dt/>").html(obj.title).appendTo($dl);
            var $ul = $("<ul/>").appendTo($dom);
            $.each(result.category.contents, function (idx, o) {
                $("<li/>").html('<i class="icon-list left"></i><span class="right">' + o.time + '</span><a href="' + contentURL + o.id + '">' + o.title + '</a>').appendTo($ul);
            });
        });
    }, "json");
}

$(function () {
});
