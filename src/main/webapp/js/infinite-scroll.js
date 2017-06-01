/**
 * Created by Michael on 2015/7/28.
 */
function initialList($container, url, query, loadCallback) {
  var offset = 0;
  var limit = 20;
  var loading = false;
  var eol = false;

  $container.empty();

  function loadItems() {
    if (loading || eol) return;

    loading = true;

    var $loaderDiv = $("<div></div>").appendTo($container).addClass("loader");

    var queryObject = _.extend({offset: offset, limit: limit}, query);

    $.getJSON(url, queryObject)
      .done(function(data) {
        loadCallback(data);
        offset += data.length;
        eol = data.length < limit;
      })
      .fail(function(jqxhr, textStatus, error) {
        var err = textStatus + ", " + error;
        console.log("Request Failed: " + err);
      })
      .always(function() {
        $loaderDiv.remove();
        loading = false;
      });

    /*
    var data = [
      {
        "uri": ".",
        "avatar": "images/avatar1.png",
        "fullName": "汤力丞",
        "mobile": "18601646631",
        "company": "某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司"
      },
      {
        "uri": ".",
        "avatar": null,
        "fullName": "汤力丞",
        "mobile": "18601646631",
        "company": "某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司"
      },
      {
        "uri": ".",
        "fullName": "汤力丞",
        "mobile": "18601646631",
        "company": "某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司"
      },
      {
        "uri": ".",
        "avatar": "images/avatar1.png",
        "fullName": "汤力丞",
        "mobile": "18601646631"
      },
      {
        "uri": ".",
        "avatar": "images/avatar2.png",
        "fullName": "汤力丞",
        "mobile": "18601646631",
        "company": "某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司"
      },
      {
        "uri": ".",
        "avatar": "images/avatar1.png",
        "fullName": "汤力丞",
        "mobile": "18601646631",
        "company": "某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司"
      },
      {
        "uri": ".",
        "avatar": null,
        "fullName": "汤力丞",
        "mobile": "18601646631",
        "company": "某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司"
      },
      {
        "uri": ".",
        "fullName": "汤力丞",
        "mobile": "18601646631",
        "company": "某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司"
      },
      {
        "uri": ".",
        "avatar": "images/avatar1.png",
        "fullName": "汤力丞",
        "mobile": "18601646631"
      },
      {
        "uri": ".",
        "avatar": "images/avatar2.png",
        "fullName": "汤力丞",
        "mobile": "18601646631",
        "company": "某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司"
      },
      {
        "uri": ".",
        "avatar": "images/avatar1.png",
        "fullName": "汤力丞",
        "mobile": "18601646631",
        "company": "某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司"
      },
      {
        "uri": ".",
        "avatar": null,
        "fullName": "汤力丞",
        "mobile": "18601646631",
        "company": "某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司"
      },
      {
        "uri": ".",
        "fullName": "汤力丞",
        "mobile": "18601646631",
        "company": "某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司"
      },
      {
        "uri": ".",
        "avatar": "images/avatar1.png",
        "fullName": "汤力丞",
        "mobile": "18601646631"
      },
      {
        "uri": ".",
        "avatar": "images/avatar2.png",
        "fullName": "汤力丞",
        "mobile": "18601646631",
        "company": "某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司"
      },
      {
        "uri": ".",
        "avatar": "images/avatar1.png",
        "fullName": "汤力丞",
        "mobile": "18601646631",
        "company": "某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司"
      },
      {
        "uri": ".",
        "avatar": null,
        "fullName": "汤力丞",
        "mobile": "18601646631",
        "company": "某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司"
      },
      {
        "uri": ".",
        "fullName": "汤力丞",
        "mobile": "18601646631",
        "company": "某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司"
      },
      {
        "uri": ".",
        "avatar": "images/avatar1.png",
        "fullName": "汤力丞",
        "mobile": "18601646631"
      },
      {
        "uri": ".",
        "avatar": "images/avatar2.png",
        "fullName": "汤力丞",
        "mobile": "18601646631",
        "company": "某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司某公司"
      }
    ];

    setTimeout(function() {
      loadCallback(data);
      offset += data.length;
      eol = data.length < limit;

      $loaderDiv.remove();
      loading = false;
    }, 1000);
    */
  }

  loadItems();

  $(window).scroll(function() {
    var scrollTrigger = 0.8;

    if ($(window).scrollTop() / ($(document).height() - $(window).height()) > scrollTrigger) {
      //console.log('autopagerizing triggered');
      loadItems();
    }
  });
}
