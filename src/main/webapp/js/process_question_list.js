/**
 * Processes the question lists
 * Does things like indent the questions inside of a repeat, etc.
 * @author eLIPSE
 */
var process_question_list = {

    getRowText(row) {
        return row.children[1].textContent.trim();
    },

    /**
     * Sets the text of the row
     * @param {Object} row - The row object
     * @param {String} text - The text to set to
     */
    setRowText(row, text) {
        row.children[1].textContent = text;
    },

    /**
     * Indents the repeat blocks
     * @param {Array} An array of row objects
     */
    indentRepeats(rows) {
        var isRepeating = false;

        // The start and end regex pattern
        var repeatPattern = /REPEAT .*/;
        var endRepeatPattern = /ENDREPEAT/;

        for (var i = 0; i < rows.length; i++) {
            var row = rows[i];

            var rowText = this.getRowText(row);
            // Check if the row matches either the start or end
            if (repeatPattern.test(rowText)) {
                isRepeating = true;
            } else if (endRepeatPattern.test(rowText)) {
                isRepeating = false;
            } else if (isRepeating) {
                // Indent the block
                row.children[1].style.paddingLeft = "30px";
            }
        }
    },

    /**
     * Processes the question list
     * Should be called with the page has finished loading
     */
    init: function() {
        try {
            var rows = $('.question-list > tbody')[0].children;

            this.indentRepeats(rows);
        } catch (err) {
            // The question list is empty
        }

    }
};

process_question_list.init();
