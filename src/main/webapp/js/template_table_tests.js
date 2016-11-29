var expect = chai.expect;

describe('Template Table', function() {

    /**
     * Constructor
     */
    describe('Module State', function() {
        it('tableRows should exist', function() {
            expect(template_table.tableRows).to.not.be.undefined;
        });

        it('currentOrder should exists', function() {
            expect(template_table.config.currentOrder).to.not.be.undefined;
        });

        it('sortOptions has expected sort methods', function() {
                expect(Object.keys(template_table.config.sortOptions)).to.deep.equal(['name-asc', 'name-des', 'uploaded-asc', 'uploaded-des', 'author-asc', 'author-des']);
        });
    });

    describe('setIcon', function() {
        it('setIcon should throw an error for an invalid mode', function() {
            function errMethod() {
                return template_table.setIcon('#temp', 'invalid');
            }

            expect(errMethod).to.throw(/Mode is not valid/);

        });

        it('setIcon should work for the parameter "ascending"', function() {
            function errMethod() {
                return template_table.setIcon('#temp', 'ascending');
            }

            expect(errMethod).to.not.throw(/Mode is not valid/);
        });

        it('setIcon should work for the parameter "descending"', function() {
            function errMethod() {
                return template_table.setIcon('#temp', 'descending');
            }

            expect(errMethod).to.not.throw(/Mode is not valid/);
        });

        it('setIcon should work for the parameter "none"', function() {
            function errMethod() {
                return template_table.setIcon('#temp', 'none');
            }

            expect(errMethod).to.not.throw(/Mode is not valid/);
        });
    });

    describe('setMode', function() {
        it ('Internal state should match expected', function() {
            var currentMode = template_table.config.mode;
            template_table.setMode('test mode');
            expect(template_table.config.mode).to.equal('test mode');

            // Reset to default
            template_table.setMode(currentMode);
        });

        it ('getMode should match expected', function() {
            var currentMode = template_table.config.mode;
            template_table.setMode('test mode');
            expect(template_table.getMode()).to.equal('test mode');

            // Reset to default
            template_table.setMode(currentMode);
        });


    });

    /**
     * Test the getInfo function
     */
    describe('getInfo', function() {
        // Test html string
        var testHTML = '<tr class="row-light"> <td> <input id="selectedIds3" name="selectedIds" type="checkbox" value="87"><input type="hidden" name="_selectedIds" value="on"></td><td> <p><a href="template.htm?id=87">Test Title</a></p><p>Test Description</p><p> Download examples: <a href="/smartass-dev/download.htm?scope=1&amp;id=87&amp;kind=0">[questions]</a> <a href="/smartass-dev/download.htm?scope=1&amp;id=87&amp;kind=1">[solutions]</a> <a href="/smartass-dev/download.htm?scope=1&amp;id=87&amp;kind=2">[short answers]</a> </p></td><td>Test Date</td><td>Test Author</td></tr>';

        // Get the html row
        var row = $.parseHTML(testHTML)[0];

        var data = template_table.getInfo(row);

        var expected = {
            author: 'Test Author',
            description: 'Test Description',
            name: 'Test Title',
            uploaded: 'Test Date'
        };

        it('should return correct JSON', function() {
            expect(data.author).to.equal(expected.author);
            expect(data.description).to.equal(expected.description);
            expect(data.name).to.equal(expected.name);
            expect(data.uploaded).to.equal(expected.uploaded);
        });
    });

    /**
     * Tests the getInfo function for the repository mode
     */
    describe('getInfo for repository', function() {

        var testHtml = "<tr class=\"table-row\"> <td> <a href=\"/smartass-dev/template.htm?id=220\"> name </a> <p>description</p></td><td> <p>date</p></td><td> <p>author</p></td></tr>";

        var row = $.parseHTML(testHtml)[0];

        var expected = {
            author: 'author',
            description: 'description',
            name: 'name',
            uploaded: 'date'
        };

        it('should return correct JSON', function() {
            // Set the mode
            template_table.setMode('repository');
            
            var actual = template_table.getInfo(row);

            expect(actual.author).to.equal(expected.author);
            expect(actual.description).to.equal(expected.description);
            expect(actual.name).to.equal(expected.name);
            expect(actual.uploaded).to.equal(expected.uploaded);

            template_table.setMode('selectForm');
        });
    });

    /**
     * Tests getInfo for the assignment mode
     */
    describe('getInfo for assignments', function() {
        var testHtml = "<tr class=\"table-row\"> <td width=\"15%\">date</td><td> name </td><td> author </td><td width=\"15%\"> <a href=\"composer.htm?new=1&amp;id=35\">copy</a> </td></tr>";

        var row = $.parseHTML(testHtml)[0];

        var expected = {
            author: 'author',
            description: '',
            name: 'name',
            uploaded: 'date'
        };

        it('should return correct JSON', function() {
            // Set the mode
            template_table.setMode('assignments');
            
            var actual = template_table.getInfo(row);

            expect(actual.author).to.equal(expected.author);
            expect(actual.description).to.equal(expected.description);
            expect(actual.name).to.equal(expected.name);
            expect(actual.uploaded).to.equal(expected.uploaded);

            template_table.setMode('selectForm');
        });
    });

    /**
     * Test sorting based on the name
     */
    describe('sortTable', function() {

        it('sortTable should throw an error for invalid ordering methods', function() {

            function errMethod() {
                template_table.sortTable('invalid');
            }

            expect(errMethod).to.throw(/Sort order is not valid/);

        });

        it('Name is sorted correctly', function() {
            // Title = A
            var htmlA = '<tr class="row-light"> <td> <input id="selectedIds1" name="selectedIds" type="checkbox" value="87"><input type="hidden" name="_selectedIds" value="on"></td><td> <p><a href="template.htm?id=87">A</a></p><p>Test Description</p><p> Download examples: <a href="/smartass-dev/download.htm?scope=1&amp;id=87&amp;kind=0">[questions]</a> <a href="/smartass-dev/download.htm?scope=1&amp;id=87&amp;kind=1">[solutions]</a> <a href="/smartass-dev/download.htm?scope=1&amp;id=87&amp;kind=2">[short answers]</a> </p></td><td>Test Date</td><td>Test Author</td></tr>';
            // Title = B
            var htmlB = '<tr class="row-light"> <td> <input id="selectedIds1" name="selectedIds" type="checkbox" value="87"><input type="hidden" name="_selectedIds" value="on"></td><td> <p><a href="template.htm?id=87">B</a></p><p>Test Description</p><p> Download examples: <a href="/smartass-dev/download.htm?scope=1&amp;id=87&amp;kind=0">[questions]</a> <a href="/smartass-dev/download.htm?scope=1&amp;id=87&amp;kind=1">[solutions]</a> <a href="/smartass-dev/download.htm?scope=1&amp;id=87&amp;kind=2">[short answers]</a> </p></td><td>Test Date</td><td>Test Author</td></tr>';

            var rows = []; 
            rows.push($.parseHTML(htmlA)[0]);
            rows.push($.parseHTML(htmlB)[0]);

            var oldRows = template_table.tableRows;

            template_table.tableRows = rows;


            template_table.sortTable('name-asc');
            var info = template_table.getInfo(template_table.tableRows[0]);
            expect(info.name).to.equal('A');

            template_table.sortTable('name-des');
            info = template_table.getInfo(template_table.tableRows[0]);
            expect(info.name).to.equal('B');

            template_table.tableRows = oldRows; 
        });

        it('Uploaded is sorted correctly', function() {

            // Uploaded = 2010
            var htmlA = '<tr class="row-light"> <td> <input id="selectedIds1" name="selectedIds" type="checkbox" value="87"><input type="hidden" name="_selectedIds" value="on"></td><td> <p><a href="template.htm?id=87">C</a></p><p>Test Description</p><p> Download examples: <a href="/smartass-dev/download.htm?scope=1&amp;id=87&amp;kind=0">[questions]</a> <a href="/smartass-dev/download.htm?scope=1&amp;id=87&amp;kind=1">[solutions]</a> <a href="/smartass-dev/download.htm?scope=1&amp;id=87&amp;kind=2">[short answers]</a> </p></td><td>2010</td><td>Test Author</td></tr>';
            // Uploaded = 2011
            var htmlB = '<tr class="row-light"> <td> <input id="selectedIds1" name="selectedIds" type="checkbox" value="87"><input type="hidden" name="_selectedIds" value="on"></td><td> <p><a href="template.htm?id=87">C</a></p><p>Test Description</p><p> Download examples: <a href="/smartass-dev/download.htm?scope=1&amp;id=87&amp;kind=0">[questions]</a> <a href="/smartass-dev/download.htm?scope=1&amp;id=87&amp;kind=1">[solutions]</a> <a href="/smartass-dev/download.htm?scope=1&amp;id=87&amp;kind=2">[short answers]</a> </p></td><td>2011</td><td>Test Author</td></tr>';

            var rows = []; 
            rows.push($.parseHTML(htmlA)[0]);
            rows.push($.parseHTML(htmlB)[0]);

            var oldRows = template_table.tableRows;

            template_table.tableRows = rows;

            template_table.sortTable('uploaded-asc');
            var info = template_table.getInfo(template_table.tableRows[0]);
            expect(info.uploaded).to.equal('2010');

            template_table.sortTable('uploaded-des');
            info = template_table.getInfo(template_table.tableRows[0]);
            expect(info.uploaded).to.equal('2011');

            template_table.tableRows = oldRows;
        });

        it('Author is sorted correctly', function() {

            // Author = Alice
            var htmlA = '<tr class="row-light"> <td> <input id="selectedIds1" name="selectedIds" type="checkbox" value="87"><input type="hidden" name="_selectedIds" value="on"></td><td> <p><a href="template.htm?id=87">C</a></p><p>Test Description</p><p> Download examples: <a href="/smartass-dev/download.htm?scope=1&amp;id=87&amp;kind=0">[questions]</a> <a href="/smartass-dev/download.htm?scope=1&amp;id=87&amp;kind=1">[solutions]</a> <a href="/smartass-dev/download.htm?scope=1&amp;id=87&amp;kind=2">[short answers]</a> </p></td><td>2011</td><td>Alice</td></tr>';
            // Author = Bob
            var htmlB = '<tr class="row-light"> <td> <input id="selectedIds1" name="selectedIds" type="checkbox" value="87"><input type="hidden" name="_selectedIds" value="on"></td><td> <p><a href="template.htm?id=87">C</a></p><p>Test Description</p><p> Download examples: <a href="/smartass-dev/download.htm?scope=1&amp;id=87&amp;kind=0">[questions]</a> <a href="/smartass-dev/download.htm?scope=1&amp;id=87&amp;kind=1">[solutions]</a> <a href="/smartass-dev/download.htm?scope=1&amp;id=87&amp;kind=2">[short answers]</a> </p></td><td>2011</td><td>Bob</td></tr>';

            var rows = []; 
            rows.push($.parseHTML(htmlA)[0]);
            rows.push($.parseHTML(htmlB)[0]);

            var oldRows = template_table.tableRows;

            template_table.tableRows = rows;

            template_table.sortTable('author-asc');
            var info = template_table.getInfo(template_table.tableRows[0]);
            expect(info.author).to.equal('Alice');

            template_table.sortTable('author-des');
            info = template_table.getInfo(template_table.tableRows[0]);
            expect(info.author).to.equal('Bob');

            template_table.tableRows = oldRows;
        });

    });


    describe('fuzzySearch', function() {

        var testInfo = {
            name: 'test string',
            description: 'a description with information',
            author: 'author',
            uploaded: '2016'
        };

        it('"test" correctly matches to testInfo.name', function() {
            expect(template_table.fuzzySearch('test', testInfo)).to.be.true;
        });

        it('"description" correctly matches to testInfo.description', function() {
            expect(template_table.fuzzySearch('description', testInfo)).to.be.true;
        });

        it('"author" correctly matches to testInfo.author', function() {
            expect(template_table.fuzzySearch('author', testInfo)).to.be.true;    
        });

        it('"2016" correctly matches to testInfo.uploaded', function() {
            expect(template_table.fuzzySearch('2016', testInfo)).to.be.true;
        });

        it('"invalid" should not match to testInfo', function() {
            expect(template_table.fuzzySearch('invalid', testInfo)).to.be.false;
        });
    });
});
