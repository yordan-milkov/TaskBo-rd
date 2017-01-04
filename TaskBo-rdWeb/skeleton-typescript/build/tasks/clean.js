var gulp = require('gulp');
var paths = require('../paths');
var del = require('del');
var vinylPaths = require('vinyl-paths');
var rename = require('gulp-rename');
// deletes all files in the output path
gulp.task('clean', ['unbundle'], function() {
  return gulp.src([paths.output])
    .pipe(vinylPaths(del));
});
var i = 1;
gulp.task('clean-ndoe', function() {
  return gulp.src('_node_modules/**/**')
  .pipe(rename({basename:`delfile-${i++}`}))
    .pipe(vinylPaths(del));
});



