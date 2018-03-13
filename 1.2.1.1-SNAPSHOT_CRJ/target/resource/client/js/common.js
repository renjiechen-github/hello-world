/**
 * 公共信息内容
 */
var common={ 
	/**
     * 判断json数据是否为空，
     * @param {Object} data json对象 主要判断json对象是否是{}
     * @return true为空 false不为空
     */
    isJsomEmpty: function(data){  
        try { 
            for (var key in data) {
                return false;
            }
        }
        
        catch (e) {
            return false;
        }
        return true;
    },
	/**
	 * 验证参数是否为空
	 */
	isEmpty:function(val){
		if(val == undefined||val == '' || val == null || val == 'null'||val == "undefined"){
			return true;
		}
		return false;
	}
}

Validator = {
    mode: "",
    Require: /.+/,
    Email: /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
    Phone: /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/,
    Msisdn: /^(147|150|151|152|158|159|182|183|187|188|13[4-9]{1})[0-9]{8}$/,
    Mobile: /^1(3|4|5|7|8)[0-9]{9}$/,
    Url: /^http:\/\/[A-Za-z0-9]+\.[A-Za-z0-9]+[\/=\?%\-&_~`@[\]\':+!]*([^<>\"\"])*$/,
    IdCard: "this.IsIdCard(value)",
    Currency: /^\d+(\.\d+)?$/,
    Number: /^\d+$/,
    Zip: /^[1-9]\d{5}$/,
    QQ: /^[1-9]\d{4,8}$/,
    Integer: /^[-\+]?\d+$/,
    Integer2: /^\d+$/,
    Integer3: /^[1-9]\d*(\.\d+)?$/,
    IP: /^(\d+)\.(\d+)\.(\d+)\.(\d+)$/,
    Double: /^[-\+]?\d+(\.\d+)?$/,
    Money:/^\d+(\.\d+)?$/,
    English: /^[A-Za-z]+$/,
    Chinese: /^[\u0391-\uFFE5]+$/,
    Username: /^[a-z]\w{3,}$/i,
    UnSafe: /^(([A-Z]*|[a-z]*|\d*|[-_\~!@#\$%\^&\*\.\(\)\[\]\{\}<>\?\\\/\'\"]*)|.{0,5})$|\s/,
    IsSafe: function(str){
        return !this.UnSafe.test(str);
    },
    SafeString: "this.IsSafe(value)",
    Filter: "this.DoFilter(value, getAttribute('accept'))",
    Limit: "this.limit(value.length,getAttribute('min'),  getAttribute('max'))",
    LimitB: "this.limit(this.LenB(value), getAttribute('min'), getAttribute('max'))",
    Date: "this.IsDate(value, getAttribute('min'), getAttribute('format'))",
    Repeat: "value == document.getElementsByName(getAttribute('to'))[0].value",
    Range: "getAttribute('min') <= (value|0) && (value|0) <= getAttribute('max')",
    Compare: "this.compare(value,getAttribute('operator'),getAttribute('to'))",
    Custom: "this.Exec(value, getAttribute('regexp'))",
    Group: "this.MustChecked(getAttribute('name'), getAttribute('min'), getAttribute('max'))",
    ErrorItem: [document.forms[0]],
    ErrorMessage: [], 
    Validate: function(theForm, mode){
        mode = mode || 1;
        this.mode = mode;
        try {
            theForm = document.getElementById(theForm);
        } 
        catch (e) {
        }
        var obj = theForm || event.srcElement;
        var count = obj.elements == undefined ? obj.all.length : obj.elements.length;//modify chang
        this.ErrorMessage.length = 1;
        this.ErrorItem.length = 1;
        this.ErrorItem[0] = obj;
        for (var i = 0; i < count; i++) {
            var foundError = false;
            with ((obj.elements == undefined ? obj.all[i] : obj.elements[i])) {
                var _dataType = getAttribute("dataType");
                
                //alert(_dataType);
                if (typeof(_dataType) == "object" || typeof(this[_dataType]) == "undefined") {
                    continue;
                }
                this.ClearState((obj.elements == undefined ? obj.all[i] : obj.elements[i]));
                if ((obj.elements == undefined ? obj.all[i] : obj.elements[i]).disabled) {
                    continue;
                }
                if (getAttribute("require") == "false" && value == "") {
                    continue;
                }
                switch (_dataType) {
                    case "IdCard":
                    case "Date":
                    case "Repeat":
                    case "Range":
                    case "Compare":
                    case "Custom":
                    case "Group":
                    case "Limit":
                    case "LimitB":
                    case "SafeString":
                    case "Filter":
                        if (!eval(this[_dataType])) {
                            this.AddError(i, getAttribute("msg"));
                            if (mode == 1) {
                                foundError = true;
                            }
                        }
                        break;
                    default:
                        if (!this[_dataType].test(value.trim())) {
                        
                            this.AddError(i, getAttribute("msg"));
                            if (mode == 1) {
                                foundError = true;
                            }
                        }
                        break;
                }
            }
            if (foundError == true) {
                break;
            }
        }
        if (this.ErrorMessage.length > 1) {
            var errCount = this.ErrorItem.length;
            switch (mode) {
                case 1:
                //	alert(this.ErrorMessage.join("\n"));
                	njyc.phone.showShortMessage(this.ErrorMessage.join(""));
                    try {
                        this.ErrorItem[1].focus();
                    } 
                    catch (e) {
                    }
                    ;                    break;
                case 2:
                    for (var i = 1; i < errCount; i++) {
                        try {
                            var span = document.createElement("SPAN");
                            span.id = "__ErrorMessagePanel";
                            span.style.color = "red";
                            span.style.fontSize = "12px";
                            this.ErrorItem[i].parentNode.appendChild(span);
                            span.innerHTML = this.ErrorMessage[i].replace(/\d+:/, "*");
                        } 
                        catch (e) {
                            alert(e.description);
                        }
                    }
                    try {
                        this.ErrorItem[1].focus();
                    } 
                    catch (e) {
                    }
                    ;                    break;
                default:
                    this.ErrorMessage = ["\u4ee5\u4e0b\u539f\u56e0\u5bfc\u81f4\u63d0\u4ea4\u5931\u8d25\uff1a\t\t\t\t"];
                	njyc.phone.showShortMessage(this.ErrorMessage.join(""));
                    break;
            }
            return false;
        }
        return true;
    },
    limit: function(len, min, max){
        min = min || 0;
        max = max || Number.MAX_VALUE;
        return min <= len && len <= max;
    },
    LenB: function(str){
        return str.replace(/[^\x00-\xff]/g, "**").length;
    },
    ClearState: function(elem){
        with (elem) {
            if (style.color == "red") {
                style.color = "";
            }
            var lastNode = parentNode.childNodes[parentNode.childNodes.length - 1];
            if (lastNode.id == "__ErrorMessagePanel") {
                parentNode.removeChild(lastNode);
            }
        }
    },
    AddError: function(index, str){
        this.ErrorItem[this.ErrorItem.length] = (this.ErrorItem[0].elements == undefined ? this.ErrorItem[0].all[index] : this.ErrorItem[0].elements[index]);
        if (this.mode == 1) {
            this.ErrorMessage[this.ErrorMessage.length] = str;
        }
        else {
            this.ErrorMessage[this.ErrorMessage.length] = this.ErrorMessage.length + ":" + str;
        }
    },
    Exec: function(op, reg){
        return new RegExp(reg, "g").test(op);
    },
    compare: function(op1, operator, op2){
        switch (operator) {
            case "NotEqual":
                return (op1 != op2);
            case "GreaterThan":
                return (op1 > op2);
            case "GreaterThanEqual":
                return (op1 >= op2);
            case "LessThan":
                return (op1 < op2);
            case "LessThanEqual":
                return (op1 <= op2);
            default:
                return (op1 == op2);
        }
    },
    MustChecked: function(name, min, max){
        var groups = document.getElementsByName(name);
        var hasChecked = 0;
        min = min || 1;
        max = max || groups.length;
        for (var i = groups.length - 1; i >= 0; i--) {
            if (groups[i].checked) {
                hasChecked++;
            }
        }
        return min <= hasChecked && hasChecked <= max;
    },
    DoFilter: function(input, filter){
        return new RegExp("^.+.(?=EXT)(EXT)$".replace(/EXT/g, filter.split(/\s*,\s*/).join("|")), "gi").test(input);
    },
    IsIdCard: function(number){
    	var number = number.toLowerCase();
        var date, Ai;
        var verify = "10x98765432";
        var Wi = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2];
        var area = ["", "", "", "", "", "", "", "", "", "", "", "\u5317\u4eac", "\u5929\u6d25", "\u6cb3\u5317", "\u5c71\u897f", "\u5185\u8499\u53e4", "", "", "", "", "", "\u8fbd\u5b81", "\u5409\u6797", "\u9ed1\u9f99\u6c5f", "", "", "", "", "", "", "", "\u4e0a\u6d77", "\u6c5f\u82cf", "\u6d59\u6c5f", "\u5b89\u5fae", "\u798f\u5efa", "\u6c5f\u897f", "\u5c71\u4e1c", "", "", "", "\u6cb3\u5357", "\u6e56\u5317", "\u6e56\u5357", "\u5e7f\u4e1c", "\u5e7f\u897f", "\u6d77\u5357", "", "", "", "\u91cd\u5e86", "\u56db\u5ddd", "\u8d35\u5dde", "\u4e91\u5357", "\u897f\u85cf", "", "", "", "", "", "", "\u9655\u897f", "\u7518\u8083", "\u9752\u6d77", "\u5b81\u590f", "\u65b0\u7586", "", "", "", "", "", "\u53f0\u6e7e", "", "", "", "", "", "", "", "", "", "\u9999\u6e2f", "\u6fb3\u95e8", "", "", "", "", "", "", "", "", "\u56fd\u5916"];
        var re = number.match(/^(\d{2})\d{4}(((\d{2})(\d{2})(\d{2})(\d{3}))|((\d{4})(\d{2})(\d{2})(\d{3}[x\d])))$/i);
        if (re == null) {
            return false;
        }
        if (re[1] >= area.length || area[re[1]] == "") {
            return false;
        }
        if (re[2].length == 12) {
            Ai = number.substr(0, 17);
            date = [re[9], re[10], re[11]].join("-");
        }
        else {
            Ai = number.substr(0, 6) + "19" + number.substr(6);
            date = ["19" + re[4], re[5], re[6]].join("-");
        }
        if (!this.IsDate(date, "ymd")) {
            return false;
        }
        var sum = 0;
        for (var i = 0; i <= 16; i++) {
            sum += Ai.charAt(i) * Wi[i];
        }
        Ai += verify.charAt(sum % 11);
        return (number.length == 15 || number.length == 18 && number == Ai);
    },
    IsDate: function(op, formatString){
        formatString = formatString || "ymd";
        var m, year, month, day;
        switch (formatString) {
            case "yyyy-mm-dd":
                m = op.match(new RegExp("^((\\d{4}))([-])(\\d{2})([-])(\\d{2})$"));
                if (m == null) {
                    return false;
                }
                day = m[6];
                month = m[4] * 1;
                year = (m[2].length == 4) ? m[2] : GetFullYear(parseInt(m[3], 10));
                break;
            case "ymd":
                m = op.match(new RegExp("^((\\d{4})|(\\d{2}))([-./])(\\d{1,2})\\4(\\d{1,2})$"));
                if (m == null) {
                    return false;
                }
                day = m[6];
                month = m[5] * 1;
                year = (m[2].length == 4) ? m[2] : GetFullYear(parseInt(m[3], 10));
                break;
            case "dmy":
                m = op.match(new RegExp("^(\\d{1,2})([-./])(\\d{1,2})\\2((\\d{4})|(\\d{2}))$"));
                if (m == null) {
                    return false;
                }
                day = m[1];
                month = m[3] * 1;
                year = (m[5].length == 4) ? m[5] : GetFullYear(parseInt(m[6], 10));
                break;
            default:
                break;
        }
        if (!parseInt(month)) {
        
            return false;
        }
        month = month == 0 ? 12 : month;
        var date = new Date(year, month - 1, day);
        return (typeof(date) == "object" && year == date.getFullYear() && month == (date.getMonth() + 1) && day == date.getDate());
        function GetFullYear(y){
            return ((y < 30 ? "20" : "19") + y) | 0;
        }
    },
    isEmpty: function(value){
        return this.Require.test(value);
    },
    isMsisdn: function(value){
        return this.Msisdn.test(value.trim());
    },
    isMobile: function(value){
        return this.Mobile.test(value.trim());
    },
    isInteger2: function(value){
        return this.Integer2.test(value);
    },
    isIP: function(value){
        if (this.IP.test(value)) {
            if (RegExp.$1 < 256 && RegExp.$2 < 256 && RegExp.$3 < 256 && RegExp.$4 < 256) 
                return true;
        }
        return false;
        
    }
    
}; 
String.prototype.replaceAll = function(s1, s2){
    return this.replace(new RegExp(s1, "gm"), s2);
}
if (window.atob == undefined) {
    function btoa(stringToEncode){
        return stringToEncode;
    }
    function atob(stringToEncode){
        return stringToEncode;
    }
};
