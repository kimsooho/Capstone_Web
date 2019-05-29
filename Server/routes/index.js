var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
  console.log(req.ip);
  res.render('index', { title: '황제펭귄 TEST' });
});



module.exports = router;
