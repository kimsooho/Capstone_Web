var express = require('express');
var router = express.Router();
var PythonShell = require('python-shell');



var options = {

  mode: 'text',

  pythonPath: '',

  pythonOptions: ['-u'],

  scriptPath: ''

};

/* GET users listing. */
router.get('/', function (req, res, next) {
  res.send('respond with a resource');
});

router.get('/test', function (req, res) {
  console.log("test");
  PythonShell.run('test.py', null, function (err, results) {
    console.log(err);
    if (err) throw err;
    
    console.log('results: %j', results);
    res.send(results);
  });
});

module.exports = router;
