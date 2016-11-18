/**
 * Processes the question lists
 * Does things like indent the questions inside of a repeat, etc.
 * @author eLIPSE
 */
var process_question_list = {

    /**
     * Returns the text contained within a row
     * @param {Object} row - The row, as a DOM object
     * @returns {String} The string with trailing whitespace removed
     */
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
        // The stack increments as it reads every REPEAT
        // Decreases with every ENDREPEAT
        var repeatingStack = 0;

        // The start and end regex pattern
        var repeatPattern = /REPEAT .*/;
        var endRepeatPattern = /ENDREPEAT/;

        for (var i = 0; i < rows.length; i++) {
            var row = rows[i];

            var rowText = this.getRowText(row);

            // If the repeatingStack is greater than 1, apply indentation
            if (repeatingStack > 0) {
                var padding = 30 * repeatingStack;

                if (endRepeatPattern.test(rowText)) {
                    padding = 30 * (repeatingStack - 1);
                }

                row.children[1].style.paddingLeft = padding.toString() + "px";
            }

            // Check if the row matches either the start or end
            if (repeatPattern.test(rowText)) {
                repeatingStack++;
            } else if (endRepeatPattern.test(rowText)) {
                repeatingStack--;
            }
        }
    },

    /**
     * Fix the text rendering by removing whitespace characters
     * @params {Object[]} rows - The rows of the assignment editor
     */
    removeTextWhitespace: function(rows) {
        for (var i = 0; i < rows.length; i++) {
            var row = rows[i];

            var elems = row.getElementsByTagName('pre');
            if (elems.length > 0) {
                // Get the 'pre' tag
                var elem = elems[0];

                elem.textContent = elem.textContent.trim();
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

            // Add more functions here as need be
            this.indentRepeats(rows);
            this.removeTextWhitespace(rows);

        } catch (err) {
            // The question list is empty
        }

    }
};

process_question_list.init();
