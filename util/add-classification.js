var Promise = require('promise');
var prompt = require('prompt');
var mysql = require('mysql');
var pool = mysql.createPool({
   connectionLimit : 10,
   host : 'localhost',
   user : 'smartass',
   password : 'SmartAss',
   database : 'SmartAssignments'
});

function getAllClassifications(){
    pool.query({
        sql: 'SELECT * FROM classifications'
    }, function(err, res, fields) {
        if (err)  {
            throw err;
        }
        return res;
    });
}

function getClassificationId(classificationName, _callback) {
    pool.query({
        sql: 'SELECT id, name FROM classifications WHERE name=?',
        values: [classificationName]
    }, function(err, res, fields) {
        if (err)  {
            throw err;
        }
        // Check to see if classification exists
        if (res.length == 0) {
           _callback(false);
        } else {
           _callback(res[0].id);
        }
    });
}

function addClassification(classificationName, parentId, _callback) {
        pool.query({
          sql: 'INSERT INTO classifications (parent_id, name) VALUES (?, ?)',
          values: [parentId, classificationName]
        }, function(err, res, fields){
          if (err) {
              throw err;
          }
          _callback(res);
        });
}

function newClassification() {
    var classificationId;

    var schema = {
        properties: {
            name: {
                message: "Enter the classification name",
                required: true
            },
            parent: {
                message: "Enter the parent classification name"
            }
        }
    };

    prompt.start();
    prompt.get(schema, function(err, result) {

        console.log("Name: " + result.name);
        console.log("Parent: " + result.parent);


        getClassificationId(result.name, function(res){
            if (res === false) {
                if (result.parent !== "") {
                    getClassificationId(result.parent, function(res){
                        console.log(res);
                        if (res === false) {
                            console.log("Parent does not exist creating it");
                            addClassification(result.parent, 0, function(res) {
                                console.log("Created a new Parent Classification with the ID of " + res.insertId);
                                console.log("Creating new classification");
                                addClassification(result.name, res.insertId, function(res) {
                                    console.log("Created a new classification with the id of " + res.insertId);
                                });
                            });
                        } else {
                            console.log(result.parent + " already exists");
                            console.log("Parent ID: " + res);
                            addClassification(result.name, res, function(res) {
                                console.log("Created a new classification with the id of " + res.insertId);
                            });
                        }
                    });
                } else {
                    console.log("Classification does not exist adding it");
                    console.log("Parent ID: 0 ");
                    addClassification(result.name, 0, function(res) {
                        console.log("Created a new classification with the id of " + res.insertId);
                    });
                }
            } else {
                console.log(result.name + " already exists");
                classificationId = res;
            }
        });
    });
    return;
}

newClassification();

