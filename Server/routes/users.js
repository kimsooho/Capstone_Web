let {PythonShell} = require('python-shell');
var express = require('express');
var router = express.Router();

var options = {

  mode: 'text',

  pythonPath: '',

  pythonOptions: ['-u'],

  scriptPath: '/home/suho/jungwon/Capstone_Web/Server/routes'

};

/* GET users listing. */
router.get('/', function (req, res, next) {
  res.send('respond with a resource');
});

router.get('/test', function (req, res) {
  console.log("test");
  PythonShell.run('test.py', options, function (err, results) {
    if(err) throw err;

    console.log('results: %j', results);
    res.send(results);
  });
});

module.exports = router;
