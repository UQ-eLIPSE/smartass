/**
 * Tests the assignment_title functionality
 * @author eLIPSE
 */

var expect = chai.expect;

describe('Assignment Title', function() {
    describe('Module State', function() {
        it('assignmentTitle.config.postUrl is set correctly', function() {
            assignmentTitle.init('testPostUrl');
            expect(assignmentTitle.config.postUrl).to.equal('testPostUrl');
        });
    });

    describe('setTitleIndicator', function() {

        // Helper function to get classes
        function getClasses() {
            var g = document.getElementById('titleIndicator');
            return g.getAttribute('class').split(' ');
        }

        // Create a fake DOM object to store the icon in
        var elem = document.createElement('div');
        elem.setAttribute('id', 'titleIndicator');
        // Hide the element
        elem.setAttribute('display', 'none');
        document.body.appendChild(elem);

        it('"notSet" should display notSet icon', function() {
            assignmentTitle.setTitleIndicator('notSet');
            
            var classes = getClasses();
            expect(classes).to.include('glyphicon-remove');
        });

        it('"loading" should display loading icon', function() {
            assignmentTitle.setTitleIndicator('loading');
            
            var classes = getClasses();
            expect(classes).to.include('glyphicon-refresh');
            expect(classes).to.include('spin-anim');
        });

        it('"set" should display success icon', function() {
            assignmentTitle.setTitleIndicator('set');
            
            var classes = getClasses();
            expect(classes).to.include('glyphicon-ok');
        });
       
        it('default case should display notSet icon', function() {
            assignmentTitle.setTitleIndicator('default_case');
            
            var classes = getClasses();
            expect(classes).to.include('glyphicon-remove');
        });
    });
});
