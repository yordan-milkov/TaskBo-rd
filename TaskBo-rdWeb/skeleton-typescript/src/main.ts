import 'bootstrap';
import {Aurelia, DOM} from 'aurelia-framework';
import {Login} from './login';

export function configure(aurelia: Aurelia) {
  aurelia.use
    .standardConfiguration()
    .developmentLogging();
    
    console.log((<any>DOM));

  // Uncomment the line below to enable animation.
  // aurelia.use.plugin('aurelia-animator-css');

  // Anyone wanting to use HTMLImports to load views, will need to install the following plugin.
  // aurelia.use.plugin('aurelia-html-import-template-loader')

  aurelia.start().then(a => {
      if (aurelia.container.get(Login).isLoggedIn) {
        a.setRoot('app');
      } else {
        a.setRoot('login');
      }
    });
}
