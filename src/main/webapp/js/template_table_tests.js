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

    /**
     * Test the getInfo function
     */
    describe('getInfo', function() {
        // Test html string
        var testHTML = '<tr class="row-light"> <td> <input id="selectedIds1" name="selectedIds" type="checkbox" value="87"><input type="hidden" name="_selectedIds" value="on"></td><td> <p><a href="template.htm?id=87">Test Title</a></p><p>Test Description</p><p> Download examples: <a href="/smartass-dev/download.htm?scope=1&amp;id=87&amp;kind=0">[questions]</a> <a href="/smartass-dev/download.htm?scope=1&amp;id=87&amp;kind=1">[solutions]</a> <a href="/smartass-dev/download.htm?scope=1&amp;id=87&amp;kind=2">[short answers]</a> </p></td><td>Test Date</td><td>Test Author</td></tr>';

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
});
