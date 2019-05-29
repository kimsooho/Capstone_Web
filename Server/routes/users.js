let PythonShell = require('python-shell');
var express = require('express');
var router = express.Router();




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
  PythonShell.PythonShell.run('test.py', null, function (err) {
    console.log("err");    
    res.send("test");
    //console.log('results: %j', results);
    //res.send(results);
  });
});

module.exports = router;
