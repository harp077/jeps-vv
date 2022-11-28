Vue.component('jeps-success-dialog', {
    template: `
        <v-dialog hide-overlay v-model="ssuccessdlg" width="200">
            <v-card>
                <v-card-title class="headline light-blue">
                    <slot name="top"></slot>
                </v-card-title>
                <v-card-text>
                    <slot name="telo"></slot>
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
            this.$store.dispatch('changeSuccessDialogState',false);
        }
    },
    computed: {
        ssuccessdlg() {
            return this.$store.getters.SuccessDialogState;
        }
    }
});