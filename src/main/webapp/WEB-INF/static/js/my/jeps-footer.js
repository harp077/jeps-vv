Vue.component('jeps-footer', {
    props: ["mail","home"],
    template: `
        <v-footer app class="teal darken-2">
            <v-layout row wrap justify-center>
                <v-flex xs12 text-xs-center white--text>
                    <slot></slot>
                    Mail = <a style="text-decoration: underline;color:yellow;" v-bind:href="mailstr">{{mail}}</a>,
                    Home = <a style="text-decoration: underline;color:yellow;" v-bind:href="home">{{home}}</a>
                </v-flex>
            </v-layout>                    
        </v-footer>    
    `,
    data: function(){
        return {
            mailstr: 'mailto:'+this.mail
        };
    }    
});
