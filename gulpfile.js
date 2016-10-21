var concat = require('gulp-concat'); // Подключаем gulp-concat (для конкатенации файлов)
var gulp = require('gulp'); // Подключаем Gulp
var less = require('gulp-less');
var minifyCSS = require('gulp-minify-css');
var browserSync = require('browser-sync'); // Подключаем Browser Sync
var proxyMiddleware = require('http-proxy-middleware');
var uglify = require('gulp-uglifyjs'); // Подключаем gulp-uglifyjs (для сжатия JS)
var bowerFiles = require('main-bower-files'); // Собирает пути всех установленных либ (для конкатенации файлов)
var del = require('del'); // Подключаем библиотеку для удаления файлов и папок
var config = require('./gulp/config');


gulp
    .task('test', function () {
        console.log('bowerFiles', bowerFiles({filter: ['**/*.js']}));
        console.log('its test task!');
    });

gulp.task('browser-sync', function () { // Создаем таск browser-sync
    var proxy = proxyMiddleware('/api', {target: 'http://localhost:8080'});
    browserSync({ // Выполняем browser Sync
        server: {
            baseDir: config.app + 'app',
            middleware: [proxy]
        },
        notify: false // Отключаем уведомления
    });

});

gulp.task('watch', ['browser-sync'], function () {
//gulp.task('watch', ['browser-sync', 'scripts', 'styles'], function () {
//   gulp.watch('app/sass/**/*.sass', ['sass']); // Наблюдение за sass файлами в папке sass
    gulp.watch(config.app + 'app/css/**/*.css', browserSync.reload); // Наблюдение за css файлами в папке css
    gulp.watch([config.app + 'app/*.html', config.app + 'app/js/**/*.html'], browserSync.reload); // Наблюдение за HTML файлами в корне проекта
    gulp.watch(config.app + 'app/js/**/*.js', browserSync.reload); // Наблюдение за JS файлами в папке js
});

gulp.task('scripts-libs', function () {
    return gulp.src(bowerFiles({filter: ['**/*.js']})) // Берем из бовера все необходимые библиотеки
        .pipe(concat('libs.min.js')) // Собираем их в кучу в новом файле libs.min.js
        .pipe(uglify()) // Сжимаем JS файл
        .pipe(gulp.dest('dist/app/js')); // Выгружаем в папку app/js
});

gulp.task('less-libs', function () {
    gulp.src(bowerFiles({filter: ['**/*.less']})) // Берем все less файлы из папки lib и дочерних, если таковые будут
        .pipe(concat('lib.min.less'))
        .pipe(less())
        .pipe(minifyCSS()) // Сжимаем
        .pipe(gulp.dest('dist/app/css/')); // Выгружаем в папку app/css
});

gulp.task('clean', function () {
    return del.sync('dist'); // Удаляем папку dist перед сборкой
});

//gulp.task('build', ['clean', 'img', 'sass', 'scripts'], function () {
gulp.task('build', ['clean', 'scripts-libs', 'less-libs'], function () {

    // var buildCss = gulp.src([ // Переносим библиотеки в продакшен
    //     'app/css/main.css',
    //     'app/css/libs.min.css'
    // ])
    //     .pipe(gulp.dest('dist/css'))
    //
    // var buildFonts = gulp.src('app/fonts/**/*') // Переносим шрифты в продакшен
    //     .pipe(gulp.dest('dist/fonts'))

    var buildJs = gulp.src(config.app + 'app/js/**/*') // Переносим скрипты в продакшен
        .pipe(gulp.dest('dist/app/js'));

    var buildHtml = gulp.src('app/*.html') // Переносим HTML в продакшен
        .pipe(gulp.dest('dist/app'));

});

gulp.task('default', ['watch']);
