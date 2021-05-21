function parse_url() {
    var url = document.location.href,
        params,
        data = {},
        tmp;

    if (url.split("?")[1] !== undefined) {
        params = url.split("?")[1].split("&")
        for (var i = 0, l = params.length; i < l; i++) {
            tmp = params[i].split("=");
            data[tmp[0]] = tmp[1];
        }
        return data;
    }
}