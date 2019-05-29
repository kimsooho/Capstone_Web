var express = require('express');
var router = express.Router();
var shell = require('python-shell');

var options = {

  mode: 'text',

  pythonPath: '',

  pythonOptions: ['-u'],

  scriptPath: '',

  args: ['value1', 'value2', 'value3']

};

/* GET users listing. */
router.get('/', function (req, res, next) {
  res.send('respond with a resource');
});

router.get('/test', function (req, res) {
  shell.PythonShell.run('../python2DB/test.py', options, function (err, result) {
    console.log(result);
  });
});

module.exports = router;
