Vue.component('jeps-header', {
    template: `
        <v-toolbar app dark color="teal darken-1">
            <v-spacer></v-spacer>
            <img src="img/logo-jeps.png" width="60" height="27"> 
            <v-toolbar-title class="white--text">
                <slot></slot>
            </v-toolbar-title>
            <img src="img/logo-jeps.png" width="60" height="27"> 
            <v-spacer></v-spacer>
        </v-toolbar>    
  `
});
