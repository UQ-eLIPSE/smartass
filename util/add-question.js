#!/usr/bin/env node

/**
 * add-question.js
 * Utility script to simplify the process of registering questions
 *
 * To run ensure node is installed
 * - Run `npm install`
 * - Run `node add-question.js`
 */

var mysql = require('mysql');
var fs = require('fs');
var path = require('path');
var readline = require('readline');
var handlebars = require('handlebars');


/*
 * Configuration
 * ==========================================
 */

var connection = mysql.createPool({
    connectionLimit: 10,
    host: 'localhost',
    user: 'smartass',
    password: 'SmartAss',
    database: 'SmartAssignments'
});

// The template directory
// On the dev server it is '/data/www/smartass/data/tex/'
// Can also be found at 'SMARTASS_HOME/resource/data/data/tex/
var templateDirectory = '/data/www/smartass/data/tex/';

var rl = readline.createInterface({
    input: process.stdin,
    output: process.stdout
});

var verbose = true;

function info(data) {
    if (verbose) {
        console.log(data);
    }
}

function ask_user(query, cb) {
    rl.question(query, function(res) {
        cb(res);
    });
}

/**
 * Prompts user for module information
 */
function get_module_info(cb) {
    ask_user("Module name (e.g. PSeriesModule): ", function(moduleName) {
        ask_user("Author name: ", function(authorName) {
            ask_user("Description: ", function(description) {
                ask_user("Keywords: ", function(keywords) {
                    ask_user("Classification: ", function(classification) {
                        var data = {
                            moduleName: moduleName,
                            authorName: authorName,
                            keywords: keywords,
                            description: description,
                            classification: classification
                        };
                        rl.close();
                        cb(data);
                    });
                });
            });
        });
    });
}

/**
 * Creates the content of the template file
 * @param {String} The name of the module
 * @return {String} The text of the template file
 */
function create_template_file(moduleName) {

    var raw = fs.readFileSync("template.tex.hbs", "utf-8");
    var template = handlebars.compile(raw);

    var data = {'moduleName': moduleName};
    var result = template(data);

    return result;
}

function save_template_file(moduleName, cb) {
    var fileContents = create_template_file(moduleName);
    var filename = moduleName + ".tex";

    var filePath = path.join(templateDirectory, filename);

    fs.writeFile(filePath, fileContents, function(err) {
        if (err) {
            throw err;
        }

        cb();
    });
}

/**
 * Gets the author id from the author table
 * Creates a new author if it doesn't exist
 */
function get_author_id(authorName, cb) {
    connection.query({
        sql: 'SELECT id, name FROM authors WHERE name=?',
        values: [authorName]
    }, function(err, res, fields) {
        if (err) {
            throw err;
        }

        if (res.length == 0) {
            // Create a new user
            info("Creating new user '" + authorName + "'");
            connection.query({
                sql: 'INSERT INTO authors (name) VALUES (?)',
                values: [authorName]
            }, function(err, res, fields) {
                if (err) {
                    throw err;
                }

                cb(res.insertId);
            });

        } else {
            info(authorName + " already exists in database");
            cb(res[0].id);
        }
    });
}

function get_classification_id(classificationName, cb) {
    connection.query({
        sql: 'SELECT id, name FROM classifications WHERE name=?',
        values: [classificationName]
    }, function(err, res, fields) {
        if (err)  {
            throw err;
        }

        if (res.length == 0) {
            //Create a new classification
           info("Classification does not exist, please add it and try again");
        } else {
            info(classificationName + "  adding classification");
            cb(res[0].id);
        }
    });
}

function add_classification(templateId, classificationId, cb) {
    connection.query({
        sql: 'INSERT INTO templates_classifications (template_id, class_id) VALUES (?, ?)',
        values: [templateId, classificationId]
    }, function(err, res, fields){
        if (err) {
            throw err;
        }
        cb();

        if (res.length == 0) {
            //Create a new classification
            add_classification(classificationName, cb);
        } else {
            info(classificationName + " already exists in database");
            cb(res[0].id);
        }
    });
}

function add_classification(classificationName, cb) {
      ask_user("Parent classification? ", function(parentClassification){
        info("Creating new category '" + classificatonName + "'");
        connection.query({
          sql: 'INSERT INTO classifications (parent_id, name) VALUES (?, ?)',
          values: [parentClassification, classificationName]
        }, function(err, res, fields){
          if (err) {
              throw err;
          }

          cb(res.insertId);
        });
      });
}


/**
 * Creates a new template in the database
 */
function create_template(templateName, authorName, keywords, description, cb) {
    
    get_author_id(authorName, function(authorId) {
        connection.query({
            sql: 'SELECT id, name FROM templates WHERE name=?',
            values: [templateName]
        }, function(err, res, fields) {
            if (err) {
                throw err;
            }

            if (res.length == 0) {
                // Create the new template
                info("Creating new template " + templateName);
                connection.query({
                    sql: 'INSERT INTO templates (name, keywords,\
                        description, author_id, hasQuestions, hasSolutions,\
                        hasShortanswers) VALUES (?, ?, ?, ?, 0, 0, 0)',
                    values: [templateName, keywords, description, authorId]
                }, function(err, res, fields) {
                    if (err) {
                        throw err;
                    }

                    cb(res.insertId);

                });

            } else {
                console.log("Template already exists in database");
                cb(res[0].id);
            }
        });
    });
}

function main() {
    get_module_info(function(data) {
        create_template(data.moduleName, data.authorName, data.keywords, data.description, function(id) {
            console.log("Entry inserted with ID " + id);
            get_classification_id(data.classification, function(classId){
                console.log("Adding a classification with an id of " + classId);
                add_classification(id, classId, function(res){
                    console.log("Classification added ");
                });
            });
            save_template_file(data.moduleName, function() {
                console.log("File saved");
            });
        });
    });
}

main();
