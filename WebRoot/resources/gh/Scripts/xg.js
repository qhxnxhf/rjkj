/// <reference path="jquery-1.8.2.js" />

/*
 * 需要的功能模块：
 * iframe自适应问题
 * 上传组件
 */

(function () {
    var _xg = window.XG = {};

    /**
        标签结构(type=table)
        <div id="container">
            <table>
                <colgroup>
                    <col/>
                </colgroup>
                <tr/>
                ...
            </table>
        </div>

        标签结构(type=ul)
        <div id="container">
            <ul>
                <li/>
                ...
            </ul>
        </div>
     */
    var _scroll = _xg.Scroll = function (containerId, type, row, scrollUnit, direction, speed, sleep) {
        var thisScroll = this;

        thisScroll.containerId = containerId;
        thisScroll.type = type ? type : "ul";
        thisScroll.row = row ? row : 1;
        thisScroll.scrollUnit = scrollUnit ? scrollUnit : 1;
        thisScroll.direction = direction ? direction : "up";
        thisScroll.speed = speed ? speed : 2000;
        thisScroll.sleep = sleep ? sleep : 2000;

        thisScroll.$container = $("#" + containerId);
        thisScroll.width = 0;
        thisScroll.height = 0;
        thisScroll.liColumn = 0;
        thisScroll.canScroll = false;
        thisScroll.scroll = 0;
        thisScroll.intervalId;

        if (thisScroll.type == "table") { thisScroll.direction = "up"; }

        var $type = thisScroll.$container.find(thisScroll.type);

        if (thisScroll.type == "table") {
            thisScroll.$container.addClass("xg-scroll-type-table");
            var $head = $("<table/>").appendTo(thisScroll.$container);
            var $bodyDiv = $("<div/>").appendTo(thisScroll.$container);
            $type.find("colgroup").clone().appendTo($head);
            $type.find("tr:first").appendTo($head);
            $type.appendTo($bodyDiv);

            thisScroll.canScroll = $type.find("tr").length > thisScroll.row;

            var borderTop = 0;
            if (!isNaN(parseInt($type.find("tr:first").children().css("border-top-width")))) {
                borderTop = parseInt($type.find("tr:first").children().css("border-top-width"));
            }
            if (thisScroll.canScroll) {
                $bodyDiv.height($type.find("tr").eq(thisScroll.row).offset().top - $type.find("tr:first").offset().top - ($.browser.msie && $.browser.version == 7 ? borderTop / 2 : 0));
            }
            $type.css("margin-top", 0 - borderTop + "px");
        }

        if (thisScroll.type == "ul") {
            thisScroll.$container.addClass("xg-scroll-type-ul");
            var $firstLi = $type.find("li:first");
            thisScroll.width = $firstLi.outerWidth(true);
            thisScroll.height = $firstLi.outerHeight(true);

            $.each($type.find("li"), function (index, li) {
                if (index == 0) {
                    thisScroll.liColumn = 1;
                }
                else {
                    if ($(li).offset().top - $(li).prev("li").offset().top > 0) {
                        return false;
                    }
                    else {
                        thisScroll.liColumn = index + 1;
                    }
                }
            });

            thisScroll.canScroll = $type.find("li").length > thisScroll.row * thisScroll.liColumn
            if (thisScroll.canScroll) {
                if (thisScroll.direction == "up") {
                    thisScroll.scroll = thisScroll.height * thisScroll.scrollUnit;
                }
                if (thisScroll.direction == "left") {
                    thisScroll.scroll = thisScroll.width * thisScroll.scrollUnit;
                }
            }

            thisScroll.$container.height(parseInt(thisScroll.$container.find("ul").css("margin-top")) + thisScroll.height * thisScroll.row);
        }

    }
    _scroll.prototype = {
        start: function () {
            var thisScroll = this;

            thisScroll.$container.hover(function () {
                thisScroll.clearInterval();
            }, function () {
                thisScroll.setInterval();
            }).trigger("mouseleave");
        },
        setInterval: function () {
            var thisScroll = this;

            if (thisScroll.type == "table") {
                if (thisScroll.canScroll) {
                    var $body = thisScroll.$container.find("div > table");
                    thisScroll.intervalId = setInterval(function () {
                        var marginTop = parseInt($body.css("margin-top"));
                        var borderTop = 0;
                        if (!isNaN(parseInt($body.find("tr:first").children().css("border-top-width")))) {
                            borderTop = parseInt($body.find("tr:first").children().css("border-top-width"));
                        }
                        thisScroll.scroll = $body.find("tr").eq(thisScroll.scrollUnit).offset().top - $body.find("tr:first").offset().top - ($.browser.msie && $.browser.version == 7 ? borderTop / 2 : 0);
                        $body.animate({ marginTop: marginTop - thisScroll.scroll + "px" }, thisScroll.speed, function () {
                            for (var i = 0; i < thisScroll.scrollUnit; i++) {
                                $body.find("tr:first").appendTo($body);
                            }
                            $body.css({ marginTop: marginTop + "px" });
                        });
                    }, thisScroll.speed + thisScroll.sleep);
                }
            }

            if (thisScroll.type == "ul") {
                if (thisScroll.canScroll) {
                    var $ul = thisScroll.$container.find("ul");
                    if (thisScroll.direction == "up") {
                        thisScroll.intervalId = setInterval(function () {
                            var marginTop = parseInt($ul.css("margin-top"));
                            $ul.animate({ marginTop: marginTop - thisScroll.scroll + "px" }, thisScroll.speed, function () {
                                for (var i = 0; i < thisScroll.liColumn * thisScroll.scrollUnit; i++) {
                                    $ul.find("li:first").appendTo($ul);
                                }
                                $ul.css({ marginTop: marginTop + "px" });
                            });
                        }, thisScroll.speed + thisScroll.sleep);
                    }
                    if (thisScroll.direction == "left") {
                        var allColumn = Math.ceil($ul.find("li").length / thisScroll.row);
                        $ul.css("width", allColumn * thisScroll.width + "px");
                        thisScroll.intervalId = setInterval(function () {
                            var marginLeft = parseInt($ul.css("margin-left"));
                            $ul.animate({ marginLeft: marginLeft - thisScroll.scroll + "px" }, thisScroll.speed, function () {
                                for (var i = 0; i < thisScroll.scrollUnit; i++) {
                                    $ul.find("li:first").appendTo($ul);
                                }
                                $ul.css({ marginLeft: marginLeft + "px" });
                            });
                        }, thisScroll.speed + thisScroll.sleep);
                    }
                }
            }
        },
        clearInterval: function () {
            var thisScroll = this;
            clearInterval(thisScroll.intervalId);
        }
    };
    _scroll.init = function (containerId, type, row, scrollUnit, direction, speed, sleep) {
        var thisScroll = new _scroll(containerId, type, row, scrollUnit, direction, speed, sleep);
        thisScroll.start();
    }

    var _pager = _xg.Pager = function (containerId, currentPage, totalPage, showPage) {
        var thisPager = this;
        thisPager.containerId = containerId;
        thisPager.$container = $("#" + containerId).addClass("xg-pager");
        thisPager.currentPage = currentPage;
        thisPager.totalPage = totalPage;
        thisPager.showPage = showPage;
        thisPager.callback = function () { };
    }
    _pager.prototype = {
        setCurrentPage: function (pageNumber) {
            var thisPager = this;
            thisPager.currentPage = pageNumber;
            thisPager.render();
            thisPager.callback();
        },
        //changedCurrentPage: function () {
        //},
        render: function () {
            var thisPager = this;
            thisPager.$container.empty();

            if (thisPager.showPage % 2 == 0) { thisPager.showPage += 1; }
            var start = thisPager.currentPage - Math.floor(thisPager.showPage / 2);
            var end = thisPager.currentPage + Math.floor(thisPager.showPage / 2);
            if (thisPager.totalPage > thisPager.showPage) {
                if (start <= 0) {
                    start = 1;
                    end = thisPager.showPage;
                }
                if ((thisPager.totalPage - end) < 0) {
                    start = thisPager.totalPage - thisPager.showPage + 1;
                    end = thisPager.totalPage;
                }
            }
            else {
                start = 1;
                end = thisPager.totalPage;
            }

            if (thisPager.currentPage == 1) { thisPager.$container.append("<span>首页</span><span>上一页</span>"); }
            else {
                $("<a/>").html("首页").click(function () { thisPager.setCurrentPage(1); }).appendTo(thisPager.$container);
                $("<a/>").html("上一页").click(function () { thisPager.setCurrentPage(thisPager.currentPage - 1); }).appendTo(thisPager.$container);
            }
            for (var i = start; i <= end; i++) {
                if (i == thisPager.currentPage) { $("<em/>").html(i).addClass("xg-pager-hover").appendTo(thisPager.$container); }
                else { $("<a/>").html(i).click(function () { thisPager.setCurrentPage(Number($(this).text())); }).appendTo(thisPager.$container); }
            }
            if (thisPager.currentPage == thisPager.totalPage) { thisPager.$container.append("<span>下一页</span><span>尾页</span>"); }
            else {
                $("<a/>").html("下一页").click(function () { thisPager.setCurrentPage(thisPager.currentPage + 1); }).appendTo(thisPager.$container);
                $("<a/>").html("尾页").click(function () { thisPager.setCurrentPage(thisPager.totalPage); }).appendTo(thisPager.$container);
            }
            thisPager.$container.append("<strong>总计" + thisPager.totalPage + "页</strong>");
        }
    };
    _pager.init = function (containerId, currentPage, totalPage, showPage) {
        var thisPager = new _pager(containerId, currentPage, totalPage, showPage);
        thisPager.render();
        return thisPager;
    };

    var _list = _xg.List = function (containerId, url, params, type, headArray, rowRender, pagerContainerId, pageSize, pagerSize, pageNumber) {
        var thisList = this;
        thisList.containerId = containerId;
        thisList.$container = $("#" + containerId).addClass("xg-list");
        thisList.url = url;
        thisList.params = params;
        thisList.type = type;
        thisList.headArray = headArray;
        thisList.rowRender = rowRender;
        thisList.pagerContainerId = pagerContainerId;
        thisList.pageSize = pageSize;
        thisList.pagerSize = pagerSize;
        thisList.pageNumber = pageNumber;

        if (!thisList.type) { thisList.type = "table"; }
        if (!thisList.pageNumber) { thisList.pageNumber = 1; }
        if (!thisList.pagerSize) { thisList.pagerSize = 9; }

        if (thisList.pageSize) {
            thisList.params.pageSize = thisList.pageSize;
            thisList.params.pageNumber = thisList.pageNumber;
        }
        else {
            if (!thisList.params) { thisList.params = {}; }
        }
        //else {
        //    thisList.params.pageSize = -1;
        //}
    }
    _list.prototype = {
        setCurrentPage: function (pageNumber) {
            var thisList = this;
            thisList.pageNumber = pageNumber;
            thisList.params.pageNumber = pageNumber;
            thisList.render();
        },
        render: function () {
            var thisList = this;
            thisList.$container.empty();

            var $list = $("<" + thisList.type + "/>").appendTo(thisList.$container);
            if (thisList.type == "table") {
                if (thisList.headArray) {
                    var $headTr = $("<tr/>").appendTo($list);
                    for (var i = 0; i < thisList.headArray.length; i++) {
                        $("<th/>").html(thisList.headArray[i]).appendTo($headTr);
                    }
                }
            }

            $.post(thisList.url, thisList.params, function (data) {
                $.each(data.currentPage, function (index, obj) {
                    $list.append(thisList.rowRender(obj));
                });
                if (thisList.pagerContainerId) {
                    var thisPager = _pager.init(thisList.pagerContainerId, thisList.pageNumber, Math.ceil(data.totalCount / thisList.pageSize), thisList.pagerSize);
                    thisPager.callback = function () { thisList.setCurrentPage(thisPager.currentPage); };
                }
            }, "json");
        }
    };
    _list.init = function (containerId, url, params, type, headArray, rowRender, pagerContainerId, pageSize, pagerSize, pageNumber) {
        var thisList = new _list(containerId, url, params, type, headArray, rowRender, pagerContainerId, pageSize, pagerSize, pageNumber);
        thisList.render();
        return thisList;
    };

    var _editor = _xg.Editor = function (containerId, initStyles) {
        var thisEditor = this;
        thisEditor.containerId = containerId;
        thisEditor.$container = $("#" + containerId).addClass("xg-editor");
        thisEditor.initStyles = "html{overflow:hidden;}body{margin:0;padding:0;}table{border-collapse:collapse;border-spacing:0;}" + (initStyles ? initStyles : "");//html溢出隐藏是为了取消默认滚动条

        thisEditor.$iframe = $("<iframe/>").attr("frameborder", "0");//添加frameborder是为了兼容ie7/ie8
        thisEditor.content = thisEditor.$container.html();
    };
    _editor.prototype = {
        render: function () {
            var thisEditor = this;

            thisEditor.$container.empty();
            thisEditor.$container.append(thisEditor.$iframe);
            thisEditor.$iframe.load(function () {
                var $iframeDoc = thisEditor.$iframe.contents();
                if ($iframeDoc.find("head").length < 0) { $("<head/>").appendTo($iframeDoc); }
                if ($iframeDoc.find("body").length < 0) { $("<body/>").appendTo($iframeDoc); }

                $iframeDoc.find("head").append("<style>" + thisEditor.initStyles + "</style>");//此种写法兼容ie7/ie8
                $iframeDoc.find("body").html(thisEditor.content);
                thisEditor.$iframe.height($iframeDoc.height());//去除ie8高度错误，待更新方法
                thisEditor.$iframe.height($iframeDoc.outerHeight(true));
            });
        }
    };
    _editor.init = function (containerId, initStyles) {
        var thisEditor = new _editor(containerId, initStyles);
        thisEditor.render();
        return thisEditor;
    };

    /**
        标签结构
        <div id="container">
            <ul>
                <li>
                    <img/>
                    <a/>
                </li>
                ...
            </ul>
        </div>
     */
    var _focus = _xg.Focus = function (containerId, speed, sleep) {
        var thisFocus = this;
        thisFocus.containerId = containerId;
        thisFocus.$container = $("#" + containerId).addClass("xg-focus");
        thisFocus.speed = speed ? speed : 2000;
        thisFocus.sleep = sleep ? sleep : 2000;

        thisFocus.$ul = thisFocus.$container.children("ul");
        thisFocus.$allLi = $($.makeArray(thisFocus.$ul.children("li")).reverse());
        thisFocus.$ul.empty().append(thisFocus.$allLi);
        $.each(thisFocus.$allLi, function (index, obj) {
            var $li = $(obj);
            $li.append($("<div/>"));
            var $span = $("<span/>").html($li.children("a").text());
            $li.children("a").empty().append($span);
        });
        thisFocus.count = thisFocus.$allLi.length;

        thisFocus.$ul.height(thisFocus.$allLi.outerHeight(true));
        thisFocus.currentIndex = 0;
        thisFocus.$tab = $("<div/>").appendTo(thisFocus.$container);

        thisFocus.intervalId;
    };
    _focus.prototype = {
        renderTab: function () {
            var thisFocus = this;

            thisFocus.$tab.empty();

            if (thisFocus.count > 0) {
                for (var i = 0; i < thisFocus.count; i++) {
                    if (i == thisFocus.currentIndex) {
                        $("<em></em>").html(i + 1).appendTo(thisFocus.$tab);
                    }
                    else {
                        $("<span></span>").html(i + 1).appendTo(thisFocus.$tab);
                    }
                }
                thisFocus.$tab.children("span").hover(function () {
                    thisFocus.clearInterval();
                    thisFocus.changeIndex(parseInt($(this).text()) - 1);
                }, null);
                thisFocus.$tab.children("em").hover(function () {
                    thisFocus.clearInterval();
                }, function () {
                    thisFocus.setInterval();
                });
            }
        },
        changeIndex: function (index) {
            var thisFocus = this;

            if (index == thisFocus.count) { index = 0; }
            var loop = index - thisFocus.currentIndex;
            if (loop < 0) { loop += thisFocus.count; }
            for (var i = 0; i < loop; i++) {
                thisFocus.$ul.children("li:last").prependTo(thisFocus.$ul);
            }
            thisFocus.currentIndex = index;
            thisFocus.renderTab();
        },
        toggle: function () {
            var thisFocus = this;

            var $lastLi = thisFocus.$ul.children("li:last");
            $lastLi.animate({ opacity: "0" }, thisFocus.speed, function () {
                $lastLi.css("opacity", "1");
                thisFocus.changeIndex(thisFocus.currentIndex + 1);
            });
        },
        setInterval: function () {
            var thisFocus = this;

            thisFocus.intervalId = setInterval(function () {
                thisFocus.toggle();
            }, thisFocus.speed + thisFocus.sleep);
        },
        clearInterval: function () {
            var thisFocus = this;

            if (thisFocus.intervalId) {
                clearInterval(thisFocus.intervalId);
            }
        }
    }
    _focus.init = function (containerId) {
        var thisFocus = new _focus(containerId);
        thisFocus.renderTab();
        thisFocus.setInterval();
        return thisFocus;
    };

    var _format = _xg.Format = {};
    _format.getDateString = function (str) {
        if (str) {
            var date = new Date(parseInt(str.replace(/\D/igm, "")));
            return date.getFullYear() + "/" + date.getMonth() + "/" + date.getDate();
        } else { return ""; }
    };
    _format.getDateTimeString = function (str) {
        if (str) {
            var date = new Date(parseInt(str.replace(/\D/igm, "")));
            return date.getFullYear() + "/" + date.getMonth() + "/" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes();
        } else { return ""; }
    };

    var _now = _xg.Now = function (containerId, chinese, hasTime) {
        var thisNow = this;
        thisNow.containerId = containerId;
        thisNow.chinese = chinese ? true : false;
        thisNow.hasTime = hasTime ? true : false;

        thisNow.$container = $("#" + containerId);
    };
    _now.prototype = {
        getString: function () {
            var thisNow = this;
            var dateTime = new Date();
            var dateTimeStr = dateTime.getFullYear();
            dateTimeStr += thisNow.chinese ? "年" : "/";
            dateTimeStr += dateTime.getMonth() < 9 ? "0" : "";
            dateTimeStr += dateTime.getMonth() + 1;
            dateTimeStr += thisNow.chinese ? "月" : "/";
            dateTimeStr += dateTime.getDate() < 10 ? "0" : "";
            dateTimeStr += dateTime.getDate();
            dateTimeStr += thisNow.chinese ? "日" : "";
            if (thisNow.hasTime) {
                dateTimeStr += dateTime.getHours() < 10 ? " 0" : " ";
                dateTimeStr += dateTime.getHours() + ":";
                dateTimeStr += dateTime.getMinutes() < 10 ? "0" : "";
                dateTimeStr += dateTime.getMinutes() + ":";
                dateTimeStr += dateTime.getSeconds() < 10 ? "0" : "";
                dateTimeStr += dateTime.getSeconds();
            }
            return dateTimeStr;
        },
        render: function (interval) {
            var thisNow = this;
            thisNow.$container.html(thisNow.getString());
            if (interval) {
                setInterval(function () {
                    thisNow.$container.html(thisNow.getString());
                }, 1000);
            }
        }
    };
    _now.init = function (containerId) {
        var thisNow = new _now(containerId, true, true);
        thisNow.render(true);
        return thisNow;
    };

    var _dialog = _xg.Dialog = function (containerId, title, hasMask, scroll, closeSecond) {
        var thisDialog = this;
        thisDialog.containerId = containerId;
        thisDialog.title = title;
        thisDialog.hasMask = hasMask;
        thisDialog.scroll = scroll;
        thisDialog.closeSecond = closeSecond;

        thisDialog.$container = $("<div/>").addClass("xg-dialog");
        thisDialog.$contentContainer = $("#" + thisDialog.containerId);
        thisDialog.$mark = $("<div/>").addClass("xg-dialog-mark");
    };
    _dialog.prototype = {
        render: function () {
            var thisDialog = this;
            if (thisDialog.hasMask) {
                thisDialog.$mark.appendTo($("body"));
            }
            thisDialog.$container.width($("#" + thisDialog.containerId).outerWidth(true)).appendTo($("body"));//先将被弹窗的div在文档流中的总宽度(包含描边和内外边距)取出赋值给包裹框
            if (thisDialog.title) {
                var $title = $("<div/>").addClass("xg-dialog-title").appendTo(thisDialog.$container);
                $("<strong/>").html("&times;").click(function () { thisDialog.close(); }).appendTo($title);
                $("<h6/>").html(thisDialog.title).appendTo($title);
            }
            thisDialog.$contentContainer.appendTo(thisDialog.$container);
        },
        open: function () {
            var thisDialog = this;
            if (thisDialog.$container.outerHeight() > $(window).height()) {
                thisDialog.$container.css("margin-top", "0");
                thisDialog.$container.css("top", $(document).scrollTop() + "px");
                if (thisDialog.scroll) {
                    thisDialog.$contentContainer.css("height", $(window).height() - 37 + "px");
                    thisDialog.$contentContainer.css("overflow", "auto");
                }
            } else {
                thisDialog.$container.css("margin-top", 0 - thisDialog.$container.outerHeight() / 2 + "px");
                thisDialog.$container.css("top", $(document).scrollTop() + $(window).height() / 2 + "px");
            }
            thisDialog.$container.css("margin-left", 0 - thisDialog.$container.outerWidth() / 2 + "px");
            thisDialog.$container.css("display", "block");

            if (thisDialog.hasMask) {
                thisDialog.$mark.css("height", $(document).height() - (thisDialog.scroll ? (($.browser.msie && $.browser.version == "8.0") ? 4 : 0) : 0) + "px");//经测试在scroll模式下IE8会多出4px
                thisDialog.$mark.css("display", "block");
            }

            if (thisDialog.closeSecond) {
                setTimeout(function () { thisDialog.close(); }, thisDialog.closeSecond * 1000);
            }
        },
        close: function () {
            var thisDialog = this;
            thisDialog.$container.css("display", "none");
            if (thisDialog.hasMask) {
                thisDialog.$mark.css("display", "none");
            }
        }
    };
    _dialog.init = function (containerId, title, closeSecond) {
        var thisDialog = new _dialog(containerId, title, true, false, closeSecond);
        thisDialog.render();
        return thisDialog;
    };

    /**
        标签结构
        <div id="container">
            <ul>
                <li>
                    <img/>
                    <a/>
                </li>
                ...
            </ul>
        </div>
     */
    var _fadeLoop = _xg.FadeLoop = function (containerId, speed, sleep) {
        var thisFadeLoop = this;
        thisFadeLoop.containerId = containerId;
        thisFadeLoop.$container = $("#" + containerId).addClass("xg-fade-loop");
        thisFadeLoop.speed = speed ? speed : 2000;
        thisFadeLoop.sleep = sleep ? sleep : 2000;
    };
    _fadeLoop.prototype = {
        setInterval: function () {
            var thisFadeLoop = this;
            var $ul = thisFadeLoop.$container.find("ul");

            setInterval(function () {
                var $liLast = $ul.find("li:last");
                $liLast.animate({ opacity: "0" }, thisFadeLoop.speed, function () {
                    $liLast.css("opacity", "1").prependTo($ul);
                });
            }, thisFadeLoop.speed + thisFadeLoop.sleep);
        }
    };
    _fadeLoop.init = function (containerId, speed, sleep) {
        var thisFadeLoop = new _fadeLoop(containerId, speed, sleep);
        thisFadeLoop.setInterval();
        return thisFadeLoop;
    };
})();
