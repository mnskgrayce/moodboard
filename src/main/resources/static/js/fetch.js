var access_key = "client_id=ciSDJprk_BRsXoaM0uQC8d5y5_bv2ekXCZv6QxquHLU";
var unsplash_API = "https://api.unsplash.com/";

function fetch_api(type, query) {
    var command, api;
    switch(type) {
        case "random": 
        command = "photos/random?count=";
        api = unsplash_API + command + query + "&" + access_key;
        break;
        case "search":
        command = "search/photos?query=";
        api = unsplash_API + command + query + "&" + access_key;

        break;
        case "get_image":
        command =  "photos/"
        api = unsplash_API + command + query + "?" + access_key;

        break;
    }

    fetch(api)
      .then((response) => response.json())
      .then((json) => {
        console.log(json);
        
        switch(type) {
            case "random": 
            insert_image(json, 6)
            break;
            case "search":
            insert_image(json.results, 8)
            break;
            case "get_image":
            load_info(json)
            break;
        }
      })
    }

function insert_image(results, frame) {
    for (
        let index = 0;
        index < results.length && index < frame;
        index++
      ) {
        var result = results[index];
        var adj_html =
          `<img src="${result.urls.raw}" class="w-100" id="${result.id}" onClick="open_post(this.id)" class="img_fluid">` +
          "<br/>";
          console.log(adj_html);

        document
          .getElementById(`frame_${index + 1}`)
          .insertAdjacentHTML("afterend", adj_html);
      }
}

 function open_post(image_id) {
    document.location.href = url =
      "post.html?id=" + encodeURIComponent(image_id);
  }

  function load_info (post) {
    var description = post.description;
    if (description == null) {
      description = post.alt_description;
    }
    var link = post.links.download;
    var source = post.urls.regular;

    document.querySelector("#image").src = source;
    document.querySelector("#description1").innerHTML +=
      " " + description + "<br/>";
    document.querySelector("#download_link").href = link;
  }

  function parse_url() {
    var url = document.location.href,
        params = url.split("?")[1].split("&"),
        data = {},
        tmp;
    for (var i = 0, l = params.length; i < l; i++) {
        tmp = params[i].split("=");
        data[tmp[0]] = tmp[1];
    }

    return data;
  }