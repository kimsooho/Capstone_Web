var express = require('express');
var router = express.Router();
var shell = require('python-shell');

var options = {

  mode: 'text',

  pythonPath: '',

  pythonOptions: ['-u'],

  scriptPath: '../python2DB/',

  args: ['value1', 'value2', 'value3']

};

/* GET users listing. */
router.get('/', function (req, res, next) {
  res.send('respond with a resource');
});

router.get('/test', function (req, res) {
  shell.PythonShell.run('test.py', options, function (err, results) {
    if (err) throw err;
    console.log('results: %j', results);
    res.send(results);
  });
});

module.exports = router;
