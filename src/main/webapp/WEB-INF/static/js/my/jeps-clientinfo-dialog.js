Vue.component('jeps-clientinfo-dialog', {
    props: ["zaglavie"],
    template: `
        <v-dialog persistent hide-overlay v-model="clientinfodlg" max-width="299">
            <v-toolbar color="light-blue">
                <v-toolbar-title class="black--text">{{zaglavie}}</v-toolbar-title>
                <v-spacer></v-spacer>
                <v-btn icon color="yellow" v-on:click.native="setfalse">
                    <v-icon>cancel</v-icon>
                </v-btn>
            </v-toolbar>    
            <v-card>
                <v-card-text>    
                    <div>
                        <div style="font-weight: bold">
                            {{zaglavie}}
                        </div>
                        <div v-for="(value, key, index) in ClientDesc">
                            {{ index }}) {{ key }} = {{ value }}
                        </div>
                        <!--{{ClientDesc.role[0].authority}}-->
                    </div> 
                </v-card-text>
            </v-card>
        </v-dialog>     
    `,
    methods: {
        setfalse() {
            this.$store.dispatch('changeClientInfoDialogState',false);
        }
    },    
    computed: {
        clientinfodlg() {
            return this.$store.getters.ClientInfoDialogState;
        },        
        ClientDesc() {
            return this.$store.getters.ClientData;
        }
    },
    created: function () {
        this.$store.dispatch('getClientData');
        //this.$root.currentClient=this.ClientDesc;
        //this.$emit('client-info-event', this.ClientDesc);
        //this.$once('client-info-event', this.ClientDesc);
    }
});
