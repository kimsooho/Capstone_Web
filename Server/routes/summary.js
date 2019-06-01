let {PythonShell} = require('python-shell');
var express = require('express');
var router = express.Router();

var options = {

  mode: 'text',

  pythonPath: '',

  pythonOptions: ['-u'],

  scriptPath:'./python2DB'
};

/* GET users listing. */
router.get('/', function (req, res, next) {
  res.send('respond with a resource');
});

//2 args
router.get('/do', function (req, res) {
  console.log("select used python");
  options.args=[req.query.roomid, req.query.memberid];

  PythonShell.run('select.py', options, function (err, results) {
    if(err) throw err;

    console.log('results: %j', results);
    res.send(results);
  });
});

module.exports = router;
