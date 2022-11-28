Vue.component('jeps-error-dialog', {
    props: ["topstr","telo"],
    template: `
        <v-dialog hide-overlay v-model="eerrordlg" max-width="200">
            <v-card>
                <v-card-title class="headline warning">
                    {{topstr}}
                </v-card-title>
                <v-card-text>
                    {{telo}}
                </v-card-text>
                <v-divider></v-divider>
                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn v-on:click.native="setfalse">Close</v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog> 
    `,
    methods: {
        setfalse() {
            this.$store.dispatch('changeErrorDialogState',false);
        }
    },
    computed: {
        eerrordlg() {
            return this.$store.getters.ErrorDialogState;
        }
    }
});