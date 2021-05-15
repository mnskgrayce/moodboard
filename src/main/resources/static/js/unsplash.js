var access_key = "client_id=ciSDJprk_BRsXoaM0uQC8d5y5_bv2ekXCZv6QxquHLU";
var unsplash_API = "https://api.unsplash.com/";

function fetch_api(type, query) {
  var command, api;
  switch (type) {
    case "random":
      command = "photos/random?count=";
      api = unsplash_API + command + query + "&" + access_key;
      break;
    case "search":
      command = "search/photos?query=";
      api = unsplash_API + command + query + "&" + access_key;
      break;
    case "get_image":
      command = "photos/";
      api = unsplash_API + command + query + "?" + access_key;

      break;
  }

  fetch(api)
    .then((response) => response.json())
    .then((json) => {
      console.log(json);

      switch (type) {
        case "random":
          insert_image(json, 10);
          break;
        case "search":
          insert_image(json.results, 10);
          break;
        case "get_image":
          load_info(json);
          break;
      }
    });
}

function open_post(image_id) {
  window.open();
  // document.location.href = url = "pic.html?id=" + encodeURIComponent(image_id);
}

function insert_image(results, frame) {
  for (let index = 0; index < results.length && index < frame; index++) {
    var result = results[index];
    var post_url = "/pic" + "?id=" + encodeURIComponent(result.id);
    var img_html =
      `<div class="bg-image hover-overlay ripple" id="mask" style="margin-top: 0.4rem">` +
      `<img src="${result.urls.regular}" class="w-100" alt="${result.alt_description}"  class="img_fluid">` +
      `<a href="${post_url}"><div class="mask" style="background-color: rgba(0, 0, 0, 0.2);"></div></a></div>`
    document.getElementById("gallery").innerHTML 
    += img_html;
  }
}

function load_info(post) {
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


