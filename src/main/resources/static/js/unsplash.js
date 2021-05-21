// Fetch images from Unsplash API for gallery and picture detail page

var access_key = "client_id=FVo_RNxmBhPBNa84mtA5AsW1_9cEy2OEUeOAoAN6nUg";
var unsplash_API = "https://api.unsplash.com/";
var number_of_image = 10;

async function fetch_api(type, query, page_num, handler) {
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
  // console.log(page_num)
  if (page_num !== undefined) {
    api = api + "&page=" + page_num;
  }

  fetch(api)
    .then((response) => response.json())
    .then((json) => {
      console.log(json);
      switch (type) {
        case "random":
          insert_image(json, number_of_image);
          break;
        case "search":
          if (page_num > json.total_pages)
            console.log("total pages", json.total_pages);
          else insert_image(json.results, number_of_image);
          break;
        case "get_image":
          load_info(json);
          break;
      }
    });

  if (handler !== undefined) {
    handler.flag = 0;
  }
}

function insert_image(results, frame) {
  for (let index = 0; index < results.length && index < frame; index++) {
    var result = results[index];
    var post_url = "/pic" + "?id=" + encodeURIComponent(result.id);
    var img_html =
      `<div class="bg-image hover-overlay" id="mask" style="margin-bottom: 0.4rem">` +
      `<img src="${result.urls.regular}" class="w-100" alt="${result.alt_description}"  class="img_fluid">` +
      `<a href="${post_url}" target="_blank"><div class="mask" style="background-color: rgba(0, 0, 0, 0.2);"></div></a></div>`;
    document.getElementById("gallery").innerHTML += img_html;
    // console.log(img_html);
  }
}

function load_info(post) {
  // var description = post.description;
  var location = post.location.name;
  // if (description == null) {
  //   description = post.alt_description;
  // }
  if (location == null) {
    location = "Unknown";
  }

  document.querySelector("#image").src = post.urls.regular;
  // document.querySelector("#description").innerHTML += description + "<br/>";
  document.querySelector("#location").innerHTML += "<br/>" + location + "<br/>";
  document.querySelector("#view").innerHTML += "<br/>" + post.views + "<br/>";
  document.querySelector("#download").innerHTML +=
    "<br/>" + post.downloads + "<br/>";
  var button = document.getElementById("download_button");
  var save_button = document.getElementById("save_button");

  // Download button
  button.addEventListener("click", async function (e) {
    const picture = await fetch(post.urls.raw);
    const picBlog = await picture.blob();
    const pictureURL = URL.createObjectURL(picBlog);

    const link = document.createElement("a");
    link.href = pictureURL;
    link.download = post.id;
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
    console.log("Download button clicked");
  });

  save_button.addEventListener("click", function (e) {
    save_button.style.display = "none";
    document.getElementById("sb-container").style.display = "block";
  });
}
