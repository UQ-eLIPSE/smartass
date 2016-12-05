/**
 * Handles setting the assignment title via AJAX
 * @author eLIPSE
 */

var assignmentTitle = {

    config: {
        postUrl: ""
    },

    /**
     * Posts the title of the assignment to the server
     */
    postTitle: function() { // The current page's URL, from spring
        var scope = this;

        var title = $("#assignmentTitle").val();

        this.setTitleIndicator('loading');

        var xmlhttp = new XMLHttpRequest();
        xmlhttp.onreadystatechange = function() {
            if (xmlhttp.status == 200) {
                var text = $('#assignmentTitle').val();
                if (text == "") {
                    scope.setTitleIndicator('notSet');
                } else {
                    scope.setTitleIndicator('set');
                }
            }
        }
        xmlhttp.open("POST", this.config.postUrl);
        xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

        var formData = new FormData();
        formData.append("_eventId_setTitle", "Set Title");
        formData.append("assignmentTitle", title);

        xmlhttp.send(formData);
    },


    /**
     * Sets the indicator for the title (The spinner in the right hand side of the input box)
     *
     * @param {String} The mode, can be either {'notSet', 'loading', 'set'}
     */
    setTitleIndicator: function(mode) {

        var ind = $('#titleIndicator');

        // Remove all the classes first
        ind.removeClass();
        ind.addClass('glyphicon form-control-feedback');

        switch (mode) {
            case 'notSet':
                ind.addClass('glyphicon-remove');
                break;

            case 'loading':
                ind.addClass('glyphicon-refresh');
                ind.addClass('spin-anim');
                break;

            case 'set':
                ind.addClass('glyphicon-ok');
                break;

            default:
                ind.addClass('glyphicon-remove');
                break;
        }
    },

    init: function(postUrl) {
        var scope = this;

        this.config.postUrl = postUrl;

        // Handle the assignment title button
        $('#assignmentTitle').on('focusout', function() {
            scope.postTitle();
        });

        /**
         * Sets the indicator to the correct icon
         */
        $(document).ready(function() {
            var text = $('#assignmentTitle').val();
            if (text == "") {
                scope.setTitleIndicator('notSet');
            } else {
                scope.setTitleIndicator('set');
            }
        });
    }
}
