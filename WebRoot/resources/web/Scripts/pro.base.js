$(function () {
    imgFade("topImg", 2000, 5000);
    topLink();
    topNav();
    content();
    picList();
    tabList();

    $(".enterprise-main td").hover(function () {
        $(this).children("h6").show(500);
        $(this).children("img").css("border-color", "#ddd");
        $(this).children("div").css("display", "block");
    }, function () {
        $(this).children("h6").hide(500);
        $(this).children("img").css("border-color", "#eee");
        $(this).children("div").css("display", "none");
    });

    $(".pager").children("a").click(function () { $(".pager").children("a").css("background-color", "#3FA8F4"); $(this).css("background-color", "#ff6a00"); });

    $(".scroll-single-line").scrollSingleLine(1000, 5000);
    //scrollMultiLine(1000, 5000);
    //$(".scroll-multi-line").scrollMultiLine(1000, 1000);
    $(".scroll-multi-line").scrollMultiLine(1000, 5000);
    //$(".scroll-table").scrollTable(5000);
    

    scrollInit();
    showListInit();
});

function topLink() {
    $(".top > div i").hover(function () {
        $(this).removeClass($(this).attr("data-icon"));
        $(this).addClass($(this).attr("data-icon") + "-hover");
    }, function () {
        $(this).removeClass($(this).attr("data-icon") + "-hover");
        $(this).addClass($(this).attr("data-icon"));
    });
}
function topNav() {
    $(".nav li").hover(function () { $(this).children("div").css("display", "block"); }, function () { $(this).children("div").css("display", "none"); $(".cookie-nav-state").css("display", "block"); });
    $(".nav li").click(function () {
        if ($(this).children("a").attr("target") == "_blank") {
            open($(this).children("a").attr("data-href"));
        }
        else {
            location.href = $(this).children("a").attr("data-href");
        }
    });
    $(".cookie-nav-state").css("display", "block");
    /* t
    $(".nav li").hover(function () { $(this).children("div").css("display", "block"); $(this).children("a").css("color", "#9a3b18"); }, function () { $(this).children("div").css("display", "none"); $(this).children("a").css("color", "#fff"); $(".cookie-nav-state").css("display", "block"); $(".cookie-nav-state").next("a").css("color", "#9a3b18"); });
    $(".nav li").click(function () {
        if ($(this).children("a").attr("target") == "_blank") {
            open($(this).children("a").attr("href"));
        }
        else {
            location.href = $(this).children("a").attr("href");
        }
    });
    $(".cookie-nav-state").css("display", "block");
    $(".cookie-nav-state").next("a").css("color", "#9a3b18");
    */
}
function content() {
    $(".content").hover(function () {
        var h1 = $(this).children("h1");
        h1.removeClass("icon-nav-" + h1.attr("data-size"));
        h1.addClass("icon-nav-" + h1.attr("data-size") + "-hover");
    }, function () {
        var h1 = $(this).children("h1");
        h1.removeClass("icon-nav-" + h1.attr("data-size") + "-hover");
        h1.addClass("icon-nav-" + h1.attr("data-size"));
    });
}
function picList() {
    $(".box div.list-pic > ul li").hover(function () {
        $(this).children("i").removeClass("icon-pic");
        $(this).children("i").addClass("icon-pic-hover");
        $(this).parent("ul").parent("div").prev("div").css("border-color", "#FAAF3B");
        $(this).parent("ul").parent("div").prev("div").children("img").attr("src", $(this).children("a").attr("data-img"));
    }, function () {
        $(this).children("i").removeClass("icon-pic-hover");
        $(this).children("i").addClass("icon-pic");
        $(this).parent("ul").parent("div").prev("div").css("border-color", "#eee");
    });
    $(".box div.list-pic > ul").each(function () {
        $(this).parent("div").prev("div").children("img").attr("src", $(this).children("li:first").children("a").attr("data-img"));
    });
}
function tabList() {
    $(".box div.tab > h2").hover(function () {
        $(this).parent("div").children("h2").css("background-color", "#3FA8F4");
        $(this).parent("div").children("h2").css("color", "#fff");
        $(this).css("background-color", "#fff");
        $(this).css("color", "#3FA8F4");
        $(this).parent("div").next("div").children("div").css("display", "none");
        $(this).parent("div").next("div").children("[data-div=" + $(this).attr("data-div") + "]").css("display", "block");
    }, function () {
    });
    $(".box div.tab").find("h2:first").css({ "background-color": "#fff", "color": "#3FA8F4" });
}

(function ($) {
    $.fn.extend({
        scrollSingleLine: function scrollSingleLine(speed, sleep) {
            $(this).hover(function () {
                clearInterval($(this).attr("data-setinterval-id"));
            }, function () {
                var $ul = $(this).children("div").children("ul");
                var scrtime;
                scrtime = setInterval(function () {
                    var liWidth = $ul.children("li:first").outerWidth();
                    $ul.animate({ marginLeft: 0 - liWidth + "px" }, speed, function () {
                        $ul.children("li:first").appendTo($ul);
                        $ul.css({ marginLeft: 0 });
                    });
                }, speed + sleep);
                $(this).attr("data-setinterval-id", scrtime);
            }).trigger("mouseleave");
        }
    });
})(jQuery);

(function ($) {
    $.fn.scrollMultiLine = function (speed, sleep) {
        $(this).hover(function () {
            clearInterval($(this).attr("data-setinterval-id"));
        }, function () {
            var $div = $(this).children("div");
            var scrtime;
            scrtime = setInterval(function () {
                var $ul = $div.children("ul:first");
                var ulHeight = $ul.children("li:first").outerHeight();
                $div.animate({ marginTop: 0 - ulHeight + "px" }, speed, function () {
                    $ul.appendTo($div);
                    $div.css({ marginTop: 0 });
                });
            }, speed + sleep);
            $(this).attr("data-setinterval-id", scrtime);
        }).trigger("mouseleave");
    }
})(jQuery);
//function scrollMultiLine(speed, multi) {
//    if (multi == null) multi = 0;
//    var scrtime;
//    $(".scroll-multi-line").hover(function () {
//        clearInterval(scrtime);
//    }, function () {
//        var $div = $(this).children("div");
//        var $ul = $div.children("ul:first");
//        $ul.appendTo($div);
//        scrtime = setInterval(function () {
//            var ulHeight = $ul.outerHeight();
//            for (var i = 0; i < multi; i++) {
//                $ul = $ul.next("ul");
//                ulHeight += $ul.outerHeight();
//                $ul.appendTo($div);
//            }
//            $div.animate({ marginTop: 0 - ulHeight + "px" }, speed, function () {
//                $div.remove("ul:first");
//                $div.css({ marginTop: 0 });
//                for (var ii = 0; ii < multi; ii++) {
//                    $div.remove("ul:first");
//                }
//                //$div.find("ul:first").fadeIn(1000);
//            });
//        }, speed + 1000);
//    }).trigger("mouseleave");
//}
//(function ($) {
//    $.fn.extend({
//        scrollTable: function scrollTable(speed) {
//            $(this).hover(function () {
//                clearInterval($(this).attr("data-setinterval-id"));
//            }, function () {
//                var $div = $(this).children("div");
//                var $table = $div.children("table");
//                var scrtime;
//                scrtime = setInterval(function () {
//                    var trHeight = $table.find("tr:first").outerHeight();// alert(trHeight);
//                    $div.animate({ marginTop: 0 - trHeight + "px" }, speed, function () {
//                        $table.find("tr:first").appendTo($table);
//                        $div.css({ marginTop: 0 });
//                    });
//                }, speed);
//                $(this).attr("data-setinterval-id", scrtime);
//            }).trigger("mouseleave");
//        }
//    });
//})(jQuery);
(function ($) {
    $.fn.extend({
        scrollTable: function scrollTable(speed,sleep) {
            var $div = $(this);
            $div.hover(function () {
                clearInterval($div.attr("data-setinterval-id"));
            }, function () {
                var $table = $div.find("table");
                var $tbody = $table.find("tbody");
                var scrtime;
                scrtime = setInterval(function () {
                    var trHeight = $table.find("tr").outerHeight();
                    $table.animate({ marginTop: 0 - trHeight + "px" }, speed, function () {
                        $tbody.find("tr:first").appendTo($tbody);
                        $table.css({ marginTop: 0 });
                    });
                }, speed + sleep);
                $div.attr("data-setinterval-id", scrtime);
            }).trigger("mouseleave");
        }
    });
})(jQuery);

function imgFade(id, speed, sleep) {
    var $id = $("#" + id);
    $id.children("div").hide();
    $id.children("div:first").show().next("div").attr("data-show", "true");
    setInterval(function () {
        $id.children("div").fadeOut(speed);
        $id.children("[data-show='true']").fadeIn(speed, function () {
            if ($id.children("div:last").attr("data-show") == "true") {
                $id.children("div:last").removeAttr("data-show");
                $id.children("div:first").attr("data-show", "true");
            } else {
                $id.children("div").removeAttr("data-show");
                $(this).next("div").attr("data-show", "true");
            }
        });
    }, sleep);
}

function scrollInit() {
    $(".scroll-single-line").each(function () {
        $(this).height($(this).children("div").children("ul").children("li:first").outerHeight());
    });
    $(".scroll-multi-line").each(function () {
        $(this).height($(this).children("div").children("ul").children("li:first").outerHeight() * 2);
    });
    $(".scroll-table").each(function () {
        $(this).height($(this).find("tbody:first").find("tr:first").outerHeight() * 11 + 1);
    });
}

function showListInit() {
    $(".show-list h3").click(function () {
        location.href = $(this).parent("li").children("a").attr("href");
    });
}


//新闻列表
function newsList2(lmurl, lmId, size, target, newsurl, w1, w2) {
    $.post(lmurl + lmId + "/" + size + ".json", {}, function (result) {
        var item = "";
        $.each(result.rows, function (idx, obj) {
            //alert(obj.docName);
            item = item + '<li style="width:' + w1 + ';"><i class="icon-list fl" ></i><a href="' + newsurl + obj.id + '" target="_blank" class="block fl" style="width:' + w2 + ';">' + obj.newsTitle + '</a><span class="fr">' + obj.pubDate + '</span></li>';

        });

        target.empty();
        target.html(item);
    }, "json");
}

//新闻列表，带更多链接
function newsList(lmurl, lmId, size, target, newsurl, moreurl, w1, w2) {
    $.post(lmurl + lmId + "/" + size + ".json", {}, function (result) {
        var item = '<div class="title"><h2>' + result.title + '</h2><span class="icon-title"></span>'
        + '<i class="icon-more"></i><a href="' + moreurl + lmId + '"  target="_blank">more</a></div><div class="list-word"><ul id="tzgg">';

        $.each(result.rows, function (idx, obj) {
            //alert(obj.docName);
            item = item + '<li style="width:' + w1 + ';"><i class="icon-list fl" ></i><a href="' + newsurl + obj.id + '" target="_blank" class="block fl" style="width:' + w2 + ';">' + obj.newsTitle + '</a><span class="fr">' + obj.pubDate + '</span></li>';

        });
        item = item + '</ul></div>';

        target.empty();
        target.html(item);
    }, "json");
}

//图文列表，带更多链接 dead
function newsPicList(lmurl, lmId, size, target, newsurl, moreurl, w1, w2, fileserverurl) {
    $.post(lmurl + lmId + "/" + size + ".json", {}, function (result) {
        var item = "";
        $.each(result.rows, function (idx, obj) {
            //alert(obj.docName);
            item = item + '<li ><i class="icon-pic fl"></i><a style="width:' + w2 + ';" class="block fl" href="' + newsurl + obj.id + '" target="_blank" data-img="' + fileserverurl + obj.logoPath + '" >' + obj.newsTitle + '</a></li>';
        });

        item = item + '<li ><i class="icon-pic fl"></i><a style="text-align: right;width:' + w2 + ';" class="block fr" href="' + moreurl + lmId + '" target="_blank" data-img="' + fileserverurl + result.lmicon + '" >' + result.title + ':>>更多</a></li>';

        target.empty();
        target.html(item);
        picList();
    }, "json");
}

//快捷导航
function navList2(lmurl, lmId, size, target) {

    $.post(lmurl + lmId + "/" + size + ".json",
            {},
            function (result) {
                var item = "";
                item = '<h2><i class="icon-title fl"></i>' + result.title + '</h2><ul>';
                $.each(result.rows, function (idx, obj) {
                    //alert(obj.docName);
                    item = item + '<li><i class="icon-list fl"></i><a href="' + obj.origin + '" target="_blank">' + obj.newsTitle + '</a></li>';

                });
                item = item + "</ul>";
                target.empty();
                target.html(item);

            },
            "json");
}

//头条热点新闻列表
function hotList(lmurl, lmId, size, target, shownews, title) {

    $.post(lmurl + lmId + "/" + size + ".json",
            {},
            function (result) {
                var item = "";
                item = '<h2><i class="icon-title fl"></i>' + title + '</h2><ol>';
                $.each(result.rows, function (idx, obj) {
                    //alert(obj.docName);
                    item = item + '<li><a href="' + shownews + obj.id + '" target="_blank">' + obj.newsTitle + '</a></li>';

                });
                item = item + "</ol>";
                target.empty();
                target.html(item);

            },
            "json");
}

//加载广告:jypl:品类（地区）代码，xh:广告位序号，urltag,imgtab:广告位显示标签。
function loadAdInfo(jypl, xh, adUrl, urltag, imgtag, fileserverurl, ggzs) {
    $.ajax({
        type: 'GET',
        url: adUrl,
        data: { "ggwlx": "1", "jypl": jypl, "xh": xh },
        error: function (xhr, msg, e) { imgtag.attr("src", ggzs); },
        success: function (msg) {
            if (msg.adObject == undefined) {
                imgtag.attr("src", ggzs);
            } else {
                var ad = msg.adObject;
                var adPath = ad.IMG;
                adPath = fileserverurl + adPath;
                //alert(ad.QYURL);
                urltag.attr("href", ad.QYURL);
                imgtag.attr("src", adPath);
            }

        }
    });
}

//图文列表
function newsPicList2(lmurl, lmId, size, target, newsurl, w1, w2, fileserverurl) {
    $.post(lmurl + lmId + "/" + size + ".json", {}, function (result) {
        var item = "";
        $.each(result.rows, function (idx, obj) {
            //alert(obj.docName);
            item = item + '<li ><i class="icon-pic fl"></i><a style="width:' + w2 + ';" class="block fl" href="' + newsurl + obj.id + '" target="_blank" data-img="' + fileserverurl + obj.logoPath + '" >' + obj.newsTitle + '</a></li>';
        });

        target.empty();
        target.html(item);
        picList();
    }, "json");
}

//图文列表：单列
function imgsScroll(lmurl, lmId, size, target, newsurl, fileserverurl) {
    $.post(lmurl + lmId + "/" + size + ".json", {}, function (result) {
        var item = '';
        $.each(result.rows, function (idx, obj) {
            //alert(obj.docName);
            item = item + '<li class="fl"><a href="' + newsurl + obj.id + '" target="_blank" title="' + obj.newsTitle + '"><h3>"' + obj.newsTitle + '"</h3><img src="' + fileserverurl + obj.logoPath + '" alt="' + obj.newsTitle + '"/></a></li>';
        });
        // item = item +"</ul>";
        target.empty();
        target.html(item);
        scrollInit();
    }, "json");
}

//图文列表:两列
function imgsScroll2(lmurl, lmId, target, newsurl, rowNum, colNum, fileserverurl) {
    var size = rowNum * colNum;
    $.post(lmurl + lmId + "/" + size + ".json", {}, function (result) {
        var item = "<ul>";
        $.each(result.rows, function (idx, obj) {
            if (idx != 0) {
                if (idx % colNum == 0) {
                    item += "</ul><ul>";
                }
            }
            item = item + '<li class="fl"><a href="' + newsurl + obj.id + '" target="_blank" ><h3>' + obj.newsTitle + '</h3><img src="' + fileserverurl + obj.logoPath + '" alt="' + obj.newsTitle + '"/></a></li>';
        });
        item = item + "</ul>";

        target.empty();
        target.html(item);
    }, "json");
}

//友情链接带图片（本地调用）
function imgsLink(lmurl, lmId, size, target, fileserverurl) {
    $.post(lmurl + lmId + "/" + size + ".json", {}, function (result) {
        var item = "<ul>";
        $.each(result.rows, function (idx, obj) {
            //alert(obj.docName);
            if (idx != 0) {
                if (idx % 6 == 0) {
                    item += "</ul><ul>";
                }
            }
            item = item + '<li class="fl"><a href="' + obj.origin + '" target="_blank" title="' + obj.newsTitle + '"><img  src="' + fileserverurl + obj.logoPath + '" alt="' + obj.newsBrief + '"/></a></li>';
            if ((idx + 1) % 6 == 0) {
                item = item + '<li class="clear"></li>';
            } else {
                item = item + '<li class="fl separator"></li>';
            }
        });
        item = item + "</ul>";
        target.empty();
        target.html(item);
        scrollInit();
    }, "json");
}



(function () {
    var _95306 = window.DZ95306 = {};
    _95306.initListPic = function (containerId, categoryUrl, categoryId, size, contentUrl, moreUrl, resourceUrl, className, separator) {
        var thisData = new _serverData(containerId, categoryUrl, categoryId, size, contentUrl, null, resourceUrl);
        thisData.getData(function (thisData) {
            _listPic.init(containerId, thisData, className, separator);
        });
    }
    //_95306.initListPicCol5
    _95306.initListWord = function (containerId, categoryUrl, categoryId, size, contentUrl, moreUrl, className, wordWidth, type, $titleRender) {
        var thisData = new _serverData(categoryUrl, categoryId, size, contentUrl, moreUrl, null)
        thisData.getData(function (thisData) {
            _listWord.init(containerId, thisData, className, wordWidth, type, $titleRender);
        });
    }
    _95306.initListBox = function (containerId, categoryUrl, categoryId, size, contentUrl, moreUrl, wordWidth) {
        _95306.initListWord(containerId, categoryUrl, categoryId, size, contentUrl, moreUrl, "dz-list-word", wordWidth, "ul", function (serverData) {
            var $title = $("<div/>").addClass("dz-list-title");
            $("<h2/>").html(serverData.data.title).appendTo($title);
            $("<span/>").addClass("icon-title").appendTo($title);
            $("<i/>").addClass("icon-more").appendTo($title);
            $("<a/>").html("more").attr("href", serverData.moreUrl + serverData.categoryId).attr("target", "_blank").appendTo($title);
            return $title;
        });
    }
    _95306.initListWordWithFocusPicSmall = function (containerId, categoryUrl, categoryId, contentUrl, moreUrl, resourceUrl) {
        var thisData = new _serverData(categoryUrl, categoryId, 6, contentUrl, moreUrl, resourceUrl);
        thisData.getData(function (thisData) {
            _listWordWithFocusPic.init(containerId, thisData, "dz-list-focus-small");
        });
    }
    _95306.initListWordWithFocusPicBig = function (containerId, categoryUrl, categoryId, contentUrl, moreUrl, resourceUrl) {
        var thisData = new _serverData(categoryUrl, categoryId, 8, contentUrl, moreUrl, resourceUrl);
        thisData.getData(function (thisData) {
            _listWordWithFocusPic.init(containerId, thisData, "dz-list-focus-big");
        });
    }
    _95306.initListNav = function (containerId, categoryUrl, categoryId, size) {
        var thisData = new _serverData(categoryUrl, categoryId, size);
        thisData.getData(function (thisData) {
            _listNav.init(containerId, thisData, "dz-list-nav", "ul");
        });
    }
    _95306.initListNavHot = function (containerId, categoryUrl, categoryId, size, contentUrl, title) {
        var thisData = new _serverData(categoryUrl, categoryId, size, contentUrl);
        thisData.getData(function (thisData) {
            _listNav.init(containerId, thisData, "dz-list-nav", "ol", title);
        });
    }

    var _serverData = _95306.ServerData = function (categoryUrl, categoryId, size, contentUrl, moreUrl, resourceUrl) {
        this.categoryUrl = categoryUrl;
        this.categoryId = categoryId;
        this.size = size;
        this.contentUrl = contentUrl;
        this.moreUrl = moreUrl;
        this.resourceUrl = resourceUrl;

        this.data;
    }
    _serverData.prototype = {
        getData: function (callback) {
            var thisData = this;
            $.post(thisData.categoryUrl + thisData.categoryId + "/" + thisData.size + ".json", {}, function (data) {
                thisData.data = data;
                if (callback) { callback(thisData); }
            }, "json");
        }
    };


    var _listPic = _95306.ListPic = function (containerId, serverData, className, separator) {
        this.containerId = containerId;
        this.$container = $("#" + containerId);
        this.serverData = serverData;
        this.className = className;
        this.separator = separator;
    }
    _listPic.prototype = {
        render: function () {
            var thisList = this;
            if (!thisList.separator) { thisList.separator = 0; }
            thisList.$container.empty().addClass(thisList.className);
            var $list = $("<ul/>").appendTo(thisList.$container);
            $.each(thisList.serverData.data.rows, function (index, row) {
                var $li = $("<li/>").addClass("fl").appendTo($list);
                var $a = $("<a/>").attr("href", thisList.contentUrl + row.id).attr("target", "_blank").appendTo($li);
                $("<h3/>").html(row.newsTitle).appendTo($a);
                $("<img/>").attr("src", thisList.resourceUrl + row.logoPath).attr("alt", row.newsTitle).appendTo($a);
                if (index > 0) {
                    var top = 0;
                    var margin = 0;
                    top = $li.offset().top - $li.prev("li").offset().top;
                    margin = Math.floor(($li.outerWidth(true) - $li.outerWidth()) / 2);

                    if (top == 0) {
                        $li.css("margin-left", (margin + thisList.separator) + "px");
                    }
                    if (top > 0) {
                        $li.css("margin-top", (margin + thisList.separator) + "px");
                    }
                    if (top < 0) {
                        $li.css("margin-top", (margin + thisList.separator) + "px").css("margin-left", (margin + thisList.separator) + "px");
                    }
                }
            });
            $("<div/>").addClass("clear").appendTo(thisList.$container);//如用此种清除无法将ul撑满
        }
    };
    _listPic.init = function (containerId, serverData, className, separator) {
        var thisList = new _listPic(containerId, serverData, className, separator);
        thisList.render();
        return thisList;
    }


    var _listWord = _95306.ListWord = function (containerId, serverData, className, wordWidth, type, $titleRender) {
        this.containerId = containerId;
        this.$container = $("#" + containerId);
        this.serverData = serverData;
        this.className = className;
        this.wordWidth = wordWidth;
        this.type = type;
        this.$titleRender = $titleRender;
    }
    _listWord.prototype = {
        render: function () {
            var thisList = this;
            if (!thisList.wordWidth) { thisList.wordWidth = 100; }
            if (!thisList.type) { thisList.type = "ul"; }

            thisList.$container.empty();
            if (thisList.$titleRender) { thisList.$titleRender(thisList.serverData).appendTo(thisList.$container); }
            var $list = $("<" + thisList.type + "/>").addClass("dz-list-word").appendTo(thisList.$container);
            $.each(thisList.serverData.data.rows, function (index, row) {
                var $li = $("<li/>").appendTo($list);
                $("<i/>").addClass("icon-list").addClass("fl").appendTo($li);
                $("<a/>").html(row.newsTitle).attr("href", thisList.serverData.contentUrl + row.id).attr("target", "_blank").addClass("block").addClass("fl").css("width", thisList.wordWidth + "px").appendTo($li);
                $("<span/>").html(row.pubDate).addClass("fr").appendTo($li);
            });
            $("<div/>").addClass("clear").appendTo(thisList.$container);//如用此种清除无法将ul撑满
        }
    };
    _listWord.init = function (containerId, serverData, className, wordWidth, type, $titleRender) {
        var thisList = new _listWord(containerId, serverData, className, wordWidth, type, $titleRender);
        thisList.render();
        return thisList;
    };


    var _listWordWithFocusPic = _95306.ListWordWithFocusPic = function (containerId, serverData, className) {
        this.containerId = containerId;
        this.$container = $("#" + containerId);
        this.serverData = serverData;
        this.className = className;
    };
    _listWordWithFocusPic.prototype = {
        render: function () {
            var thisList = this;

            thisList.$container.empty().addClass(thisList.className);
            var $focusPic = $("<div/>").addClass("fl").appendTo(thisList.$container);
            $("<img/>").appendTo($focusPic);
            var $list = $("<ul/>").appendTo(thisList.$container);
            $.each(thisList.serverData.data.rows, function (index, row) {
                var $li = $("<li/>").appendTo($list);
                $("<i/>").addClass("icon-pic").addClass("fl").appendTo($li);
                $("<a/>").html(row.newsTitle).attr("href", thisList.serverData.contentUrl + row.id).attr("target", "_blank").attr("data-img", thisList.serverData.resourceUrl + row.logoPath).addClass("block").addClass("fl").appendTo($li);
                $li.click(function () {
                    open(thisList.contentUrl + row.id);
                });
            });
            var $more = $("<li/>").appendTo($list);
            $("<i/>").addClass("icon-pic").addClass("fl").appendTo($more);
            $("<a/>").html(thisList.serverData.data.title + ":>>更多").attr("href", thisList.serverData.moreUrl + thisList.serverData.categoryId).attr("target", "_blank").attr("data-img", thisList.serverData.resourceUrl + thisList.serverData.data.lmicon).addClass("block").addClass("fr").appendTo($more);
            $list.find("li").hover(function () {
                $(this).children("i").removeClass("icon-pic");
                $(this).children("i").addClass("icon-pic-hover");
                $(this).parent(thisList.type).prev("div").css("border-color", "#FAAF3B");
                $(this).parent(thisList.type).prev("div").children("img").attr("src", $(this).children("a").attr("data-img"));
            }, function () {
                $(this).children("i").removeClass("icon-pic-hover");
                $(this).children("i").addClass("icon-pic");
                $(this).parent(thisList.type).prev("div").css("border-color", "#eee");
            });
            $focusPic.children("img").attr("src", $list.children("li:first").children("a").attr("data-img"));
        }
    };
    _listWordWithFocusPic.init = function (containerId, serverData, className) {
        var thisList = new _95306.ListWordWithFocusPic(containerId, serverData, className);
        thisList.render();
        return thisList;
    };


    var _listNav = _95306.ListNav = function (containerId, serverData, className, type, title) {
        this.containerId = containerId;
        this.$container = $("#" + containerId);
        this.serverData = serverData;
        this.className = className;
        this.type = type;
        this.title = title;
    }
    _listNav.prototype = {
        render: function () {
            var thisList = this;
            if (!thisList.type) { thisList.type = "ul"; }

            thisList.$container.empty().addClass(thisList.className);
            $h2 = $("<h2/>").html(thisList.title ? thisList.title : thisList.serverData.data.title).appendTo(thisList.$container);
            $("<i/>").addClass("icon-title").addClass("fl").appendTo($h2);
            var $list = $("<" + thisList.type + "/>").appendTo(thisList.$container);
            $.each(thisList.serverData.data.rows, function (index, row) {
                var $li = $("<li/>").appendTo($list);
                if (thisList.type == "ul") {
                    $("<i/>").addClass("icon-list").addClass("fl").appendTo($li);
                }
                $("<a/>").html(row.newsTitle).attr("href", thisList.serverData.contentUrl ? thisList.serverData.contentUrl + row.id : row.origin).attr("target", "_blank").appendTo($li);
            });
        }
    }
    _listNav.init = function (containerId, serverData, className, type, title) {
        var thisList = new _listNav(containerId, serverData, className, type, title);
        thisList.render();
        return thisList;
    };







    //var list = _95306.List = function (containerId, categoryUrl, categoryId, contentUrl, moreUrl, size, options) {
    //    this.containerId = containerId;
    //    this.$container = $("#" + containerId).addClass("dz-list");
    //    this.categoryUrl = categoryUrl;
    //    this.categoryId = categoryId;
    //    this.contentUrl = contentUrl;
    //    this.moreUrl = moreUrl;
    //    this.size = size;

    //    this.sort="";


    //    this.title = options.title;
    //    this.type = options.type;
    //    this.wordWidth = options.wordWidth;
    //    this.$rowRender = options.$rowRender;

    //    this.resourceUrl = options.resourceUrl;

    //    this.column = options.column;
    //    this.separator;

    //    this.data;
    //}
    //list.prototype = {
    //    getData: function (callback) {
    //        var thisList = this;
    //        $.post(thisList.categoryUrl + thisList.categoryId + "/" + thisList.size + ".json", {}, function (data) {
    //            thisList.data = data;
    //            callback();
    //        }, "json");
    //    },

    //    renderNewsList: function () {
    //        var thisList = this;
    //        if (!thisList.type) { thisList.type = "ul"; }
    //        if (!thisList.wordWidth) { thisList.wordWidth = 100; }
    //        if (!thisList.$rowRender) {
    //            thisList.$rowRender = function (row) {
    //                var $li = $("<li/>");
    //                $("<i/>").addClass("icon-list").addClass("fl").appendTo($li);
    //                $("<a/>").html(row.newsTitle).attr("href", thisList.contentUrl + row.id).attr("target", "_blank").addClass("width-" + thisList.wordWidth).addClass("block").addClass("fl").appendTo($li);
    //                $("<span/>").html(row.pubDate).addClass("fr").appendTo($li);
    //                return $li;
    //            }
    //        }
    //        thisList.getData(function () {
    //            thisList.$container.empty();
    //            var $title = $("<div/>").addClass("dz-list-title").appendTo(thisList.$container);
    //            $("<h2/>").html(thisList.data.title).appendTo($title);
    //            $("<span/>").addClass("icon-title").appendTo($title);
    //            $("<i/>").addClass("icon-more").appendTo($title);
    //            $("<a/>").html("more").attr("href", thisList.moreUrl).attr("target", "_blank").appendTo($title);
    //            var $list = $("<" + thisList.type + "/>").addClass("dz-list-word").appendTo(thisList.$container);
    //            $.each(thisList.data.rows, function (index, row) {
    //                $list.append(thisList.$rowRender(row));
    //            });
    //            $("<div/>").addClass("clear").appendTo(thisList.$container);
    //        });
    //    },

    //    renderNewsListWithFocusPic: function (size) {
    //        var thisList = this;
    //        if (!thisList.type) { thisList.type = "ul"; }
    //        if (!thisList.$rowRender) {
    //            thisList.$rowRender = function (row) {
    //                var $li = $("<li/>");
    //                $("<i/>").addClass("icon-pic").addClass("fl").appendTo($li);
    //                $("<a/>").html(row.newsTitle).attr("href", thisList.contentUrl + row.id).attr("target", "_blank").attr("data-img", thisList.resourceUrl + row.logoPath).addClass("block").addClass("fl").appendTo($li);
    //                $li.click(function () {
    //                    open(thisList.contentUrl + row.id);
    //                });
    //                return $li;
    //            }
    //        }
    //        thisList.getData(function () {
    //            thisList.$container.empty();
    //            var $focusPic = $("<div/>").addClass("fl").addClass("dz-list-focus-" + size).appendTo(thisList.$container);
    //            $("<img/>").appendTo($focusPic);
    //            var $list = $("<" + thisList.type + "/>").addClass("dz-list-word-focus").appendTo(thisList.$container);
    //            $.each(thisList.data.rows, function (index, row) {
    //                $list.append(thisList.$rowRender(row));
    //            });
    //            var $more = $("<li/>").appendTo($list);
    //            $("<i/>").addClass("icon-pic").addClass("fl").appendTo($more);
    //            $("<a/>").html(thisList.data.title + ":>>更多").attr("href", thisList.moreUrl + thisList.categoryId).attr("target", "_blank").attr("data-img", thisList.resourceUrl + thisList.data.lmicon).addClass("block").addClass("fr").appendTo($more);
    //            $list.find("li").hover(function () {
    //                $(this).children("i").removeClass("icon-pic");
    //                $(this).children("i").addClass("icon-pic-hover");
    //                $(this).parent(thisList.type).prev("div").css("border-color", "#FAAF3B");
    //                $(this).parent(thisList.type).prev("div").children("img").attr("src", $(this).children("a").attr("data-img"));
    //            }, function () {
    //                $(this).children("i").removeClass("icon-pic-hover");
    //                $(this).children("i").addClass("icon-pic");
    //                $(this).parent(thisList.type).prev("div").css("border-color", "#eee");
    //            });
    //            $focusPic.children("img").attr("src", $list.children("li:first").children("a").attr("data-img"));
    //            $("<div/>").addClass("clear").appendTo(thisList.$container);
    //        });
    //    },
    //    renderPicList: function (row,column,  separator) {
    //        var thisList = this;
    //        if (!thisList.type) { thisList.type = "ul"; }
    //        if (!thisList.$rowRender) {
    //            thisList.$rowRender = function (row) {
    //                var $li = $("<li/>").addClass("fl");
    //                var $a = $("<a/>").attr("href", thisList.contentUrl + row.id).attr("target", "_blank").appendTo($li);
    //                $("<h3/>").html(row.newsTitle).appendTo($a);
    //                $("<img/>").attr("src", thisList.resourceUrl + row.logoPath).attr("alt", row.newsTitle).appendTo($a);
    //                return $li;
    //            }
    //        }
    //        if (!column) { column = 5; }
    //        if (row) { thisList.size = column * row; }
    //        if (!separator) { separator = 0; }
    //        thisList.getData(function () {
    //            thisList.$container.empty().addClass("dz-pic-column-" + column)
    //            if(   .addClass("dz-scroll-single-line");
    //            var $list = $("<" + thisList.type + "/>").appendTo(thisList.$container);
    //            $.each(thisList.data.rows, function (index, row) {
    //                $list.append(thisList.$rowRender(row));
    //            });
    //            $("<div/>").addClass("clear").appendTo(thisList.$container);//如用此种清除无法将ul撑满
    //        });
    //    }

    //}
    //list.initNewsList = function (containerId, categoryUrl, categoryId, contentUrl, moreUrl, size, wordWidth, type, $rowRender) {
    //    var thisList = new DZ95306.List(containerId, categoryUrl, categoryId, contentUrl, moreUrl, size, { wordWidth: wordWidth, type: type, $rowRender: $rowRender });
    //    thisList.renderNewsList();
    //};
    //list.initNewsListWithFocusPicSmall = function (containerId, categoryUrl, categoryId, contentUrl, moreUrl, resourceUrl, type, $rowRender) {
    //    var thisList = new DZ95306.List(containerId, categoryUrl, categoryId, contentUrl, moreUrl, 6, { resourceUrl: resourceUrl, type: type, $rowRender: $rowRender });
    //    thisList.renderNewsListWithFocusPic("small");
    //};
    //list.initNewsListWithFocusPicBig = function (containerId, categoryUrl, categoryId, contentUrl, moreUrl, resourceUrl, type, $rowRender) {
    //    var thisList = new DZ95306.List(containerId, categoryUrl, categoryId, contentUrl, moreUrl, 6, { resourceUrl: resourceUrl, type: type, $rowRender: $rowRender });
    //    thisList.renderNewsListWithFocusPic("big");
    //};
    //list.initPicList = function (containerId, categoryUrl, categoryId, contentUrl, moreUrl, resourceUrl, row, column, separator, type, $rowRender) {
    //    var thisList = new DZ95306.List(containerId, categoryUrl, categoryId, contentUrl, moreUrl, row * column, { resourceUrl: resourceUrl, type: type, $rowRender: $rowRender });
    //    thisList.renderPicList(column, row, separator);
    //};
    //list.initPicListScrollSingle = function (containerId, categoryUrl, categoryId, contentUrl, moreUrl, resourceUrl, size, column, type, $rowRender) {
    //    var thisList = new DZ95306.List(containerId, categoryUrl, categoryId, contentUrl, moreUrl, size, { resourceUrl: resourceUrl, type: type, $rowRender: $rowRender });
    //    thisList.renderPicList(column);
    //};
    //list.initPicListScrollMulti = function (containerId, categoryUrl, categoryId, contentUrl, moreUrl, resourceUrl, column, type, $rowRender) { };
})();
