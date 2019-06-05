let {PythonShell} = require('python-shell');
var express = require('express');
var router = express.Router();

var options = {

  mode: 'text',

  pythonPath: '',

  pythonOptions: ['-u'],

  //args:['1', '1'],

  //scriptPath: '/home/suho/jungwon/Capstone_Web/Server/routes'
  scriptPath:'./python2DB'

};

/* GET users listing. */
router.get('/', function (req, res, next) {
  res.send('respond with a resource');
});


//2 args
router.post('/select', function (req, res) {
  var str = "RommID : " + req.body.roomid + "\nMemberID : " + req.body.memberid;
  console.log(str);

  options.args=[req.body.roomid, req.body.memberid];

  PythonShell.run('select.py', options, function (err, results) {
    if(err) throw err;

    console.log('results: %j', results);
    res.send(results);
  });
});

//3 args
router.post('/insert', function (req, res) {
  var str = "RommID : " + req.body.roomid + "\nMemberID : " + req.body.memberid+ "\nContents : "+req.body.contents;
  console.log(str);
  var contents = decodeURI(req.body.contents);
<<<<<<< HEAD
  contents.replace(/+/gi, " ");
=======
  contents = contents.replace(/+/gi, " ");
>>>>>>> 143238e690bf329cb3a0d729c65cc9fa9729fa50
  console.log(contents);
  options.args=[req.body.roomid, req.body.memberid, contents];

  PythonShell.run('insert.py', options, function (err, results) {
    if(err) throw err;

    console.log('contents: %j', results);
    res.send(results);
  });
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
