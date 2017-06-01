/**
 * Created by Michael on 2015/8/3.
 */
function initialTagsInput($container) {

  var $tagsParam = $container.find(".tags-param");
  var $tagInput = $container.find(".tag-input").val("");
  var $tagAdd = $container.find(".tag-add");
  var $tagShow = $container.find(".tags-show").empty();

  var items = $tagsParam.val() ? $tagsParam.val().split(",") : [];

  $.each(items, function(index, value) {
    appendTag(value);
  });

  var $tags = $tagShow.find(".tag-label");

  $tagAdd.click(function() {
    addTag();
  });

  $tagInput.keypress(function(event) {
    if (event.which === 13) {
      addTag();
      $(this).blur().focus();
      event.preventDefault();
    }
  });

  function addTag() {
    var newTag = $.trim($tagInput.val());
    $tagInput.val("");
    if (!newTag) return;
    var index = $.inArray(newTag, items);
    if (index > -1) {
      $tags.eq(index).hide().fadeIn();
    } else {
      $tags = $tags.add(appendTag(newTag));
      items.push(newTag);
      $tagsParam.val(items.join(","));
    }
  }

  function removeTag(index) {
    if (index > -1) {
      items.splice(index, 1);
      $tagsParam.val(items.join(","));
      $tags = $tags.not($tags.eq(index).remove());
    }
  }

  function appendTag(name) {
    return $("<span class=\"tag-label\">" + name + "</span>")
      .append($("<span class=\"glyphicon glyphicon-remove\" aria-hidden=\"true\"></span>")
        .click(function() {
          removeTag($(this).parent().index());
        })
      ).appendTo($tagShow);
  }
}
