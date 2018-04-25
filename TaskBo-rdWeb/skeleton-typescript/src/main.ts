import 'bootstrap';
import {Aurelia, DOM} from 'aurelia-framework';
import {Login} from './login';

export function configure(aurelia: Aurelia) {
  aurelia.use
    .standardConfiguration()
    .developmentLogging()
    .plugin('aurelia-dialog')
    .plugin('aurelia-autocomplete')
    .plugin('aurelia-validation')
    
    console.log((<any>DOM));

  // Uncomment the line below to enable animation.
  // aurelia.use.plugin('aurelia-animator-css');

  // Anyone wanting to use HTMLImports to load views, will need to install the following plugin.
  // aurelia.use.plugin('aurelia-html-import-template-loader')

  aurelia.start().then(a => {
      if (localStorage.getItem('UID') != null && localStorage.getItem('key') != null ) {
        a.setRoot('app');
      } else {
        a.setRoot('login');
      }
    });
}
