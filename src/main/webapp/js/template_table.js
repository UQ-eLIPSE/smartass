/**
 * Handles operations on the template table
 * @author eLIPSE
 * @requires jQuery
 */

var template_table = {
    config: {
        /* 
         * The sort options, object values is the sort function 
         * is a generator that returns the comparator
         */
        sortOptions: {
            'name-asc': function(parent) {
                return function(a, b) {
                    var name1 = parent.getInfo(a).name;
                    var name2 = parent.getInfo(b).name;

                    return name1.localeCompare(name2);
                }
            }, 

            'name-des': function(parent) {
                return function(a, b) {
                    var name1 = parent.getInfo(a).name;
                    var name2 = parent.getInfo(b).name;

                    return name2.localeCompare(name1);
                }
            }, 

            'uploaded-asc': function(parent) {
                return function(a, b) {
                    var uploaded1 = parent.getInfo(a).uploaded;
                    var uploaded2 = parent.getInfo(b).uploaded;

                    return uploaded1.localeCompare(uploaded2);
                }
            }, 

            'uploaded-des': function(parent) {
                return function(a, b) {
                    var uploaded1 = parent.getInfo(a).uploaded;
                    var uploaded2 = parent.getInfo(b).uploaded;

                    return uploaded2.localeCompare(uploaded1);
                }
            }, 

            'author-asc': function(parent) {
                return function(a, b) {
                    var author1 = parent.getInfo(a).author;
                    var author2 = parent.getInfo(b).author;

                    return author1.localeCompare(author2);
                }
            }, 

            'author-des': function(parent) {
                return function(a, b) {
                    var author1 = parent.getInfo(a).author;
                    var author2 = parent.getInfo(b).author;

                    return author2.localeCompare(author1);
                }
            }, 
        },

        /* The current ordering of the sort */
        currentOrder: 'name-asc'
    },

    /**
     * The table rows
     * This is stored in memory, so it is fine to delete 
     * the table contents within the DOM
     *
     * @returns An array of table elements
     */
    tableRows: [],

    /**
     * Sorts a table by the given order
     * @param sortOrder The sort order, must be one of the options 
     *                  within the 'sortOptions' variable
     */
    sortTable: function(sortOrder) {
        if (this.config.sortOptions[sortOrder] == undefined) {
            // Throw an error
            throw "Sort order is not valid";
        } else {
            // Sort the rows based on the given sort order
            this.tableRows.sort(this.config.sortOptions[sortOrder](this));
        }
    },

    /**
     * Updates the table on the DOM
     *
     */
    updateTable: function() {

        // Remove the old elements
        $('.row-light, .row-dark').remove();


        this.tableRows.forEach(function(row) {
            // Append to the table
            $('.panel-body > table > tbody').append(row);
        });
        
    },

    /**
     * Sorts and updates a table
     *
     */
    sortAndUpdateTable: function(sortOrder) {
        // Update the table rows, in case the user has selected a checkbox
        this.tableRows = $('.row-light, .row-dark').get();

        this.sortTable(sortOrder);

        this.updateTable();
    
    },

    /**
     * Filters the table based on a keyword
     */
    filter: function(keyword) {
        var scope = this;
        $('.row-light, .row-dark').each(function(index, item) {
            var info = scope.getInfo(item);

            // This could be improved with a fuzzy search library
            if (info.name.includes(keyword) || keyword == "") {
                $(item).show();
            } else {
                $(item).hide();
            }
        });
    },

    /**
     * Gets the information of a row as a JSON object
     * @param row A row from the 'tableRows' variable
     * @returns A JSON object containing the keys 
     *          ['title', 'description', 'uploaded', 'author']
     */
    getInfo: function(row) {
        return {
            name: row.children[1].children[0].textContent.trim(),
            description: row.children[1].children[1].textContent.trim(),
            uploaded: row.children[2].textContent.trim(),
            author: row.children[3].textContent.trim()
        }
    },

    /**
     * The init method
     *
     * Sets the table rows
     */
    init: function() {
        this.tableRows = $('.row-light, .row-dark').get();
        var scope = this;

        // Handle clicking the header buttons to change the order
        $('#name-header').click(function() {
            if (scope.config.currentOrder == 'name-asc') {
                scope.config.currentOrder = 'name-des';
            } else {
                scope.config.currentOrder = 'name-asc';
            }

            scope.sortAndUpdateTable(scope.config.currentOrder);
        });

        $('#uploaded-header').click(function() {
            if (scope.config.currentOrder == 'uploaded-asc') {
                scope.config.currentOrder = 'uploaded-des';
            } else {
                scope.config.currentOrder = 'uploaded-asc';
            }

            scope.sortAndUpdateTable(scope.config.currentOrder);
        });

        $('#author-header').click(function() {
            if (scope.config.currentOrder == 'author-asc') {
                scope.config.currentOrder = 'author-des';
            } else {
                scope.config.currentOrder = 'author-asc';
            }

            scope.sortAndUpdateTable(scope.config.currentOrder);
        });

        // Handle the filtering
        $('#nameFilter').keyup(function() {
            scope.filter($('#nameFilter').val());
        });
    }

};

template_table.init();
