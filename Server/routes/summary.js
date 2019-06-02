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

//3 args
router.post('/do', function (req, res) {
  console.log("summary");
  options.args=[req.body.roomid, req.body.memberid, req.body.ratio];
  //입력이 들어오면 사람과 방번호를 통해 모든 회의목록을 띄워
  //요약하도록 파이썬 프로그램 요청
  PythonShell.run('textsummary.py', options, function (err, results) {
    if(err) throw err;

    console.log('results: %j', results);
    res.send(results);
  });
});

module.exports = router;
