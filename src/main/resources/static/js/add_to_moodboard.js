const form = document.getElementById("add-image-form");
form.addEventListener("submit", addImageToMoodboards);

/**
 * Event handler for a form submit event.
 *
 * @see https://developer.mozilla.org/en-US/docs/Web/API/HTMLFormElement/submit_event
 *
 * @param {SubmitEvent} event
 */
async function addImageToMoodboards(event) {
  event.preventDefault();

  // Get current image apiId and checked moodboards
  var iId = document.getElementById("iId").getAttribute("value") + "";
  var moodboards = document.getElementsByClassName("moodboard-checkbox");

  // Start building the request (text because JSON does not love me)
  var request = iId;
  for (var moodboard of moodboards) {
    if (moodboard.checked) {
      var s = "," + moodboard.value;
      request += s;
    }
  }
  console.log("Save image request: " + request);

  // Send request to controller
  const fetchOptions = {
    method: "POST",
    headers: {
      "Content-Type": "text/html; charset=UTF-8",
    },
    body: request,
  };

  try {
    const response = await fetch("/moodboard/add", fetchOptions);
    if (response.ok) {
      // Disable moodboards that are already saved
      for (var moodboard of moodboards) {
        if (moodboard.checked) {
          moodboard.setAttribute("disabled", true);
        }
      }
    }
  } catch (error) {
    console.error(error);
  }
}
