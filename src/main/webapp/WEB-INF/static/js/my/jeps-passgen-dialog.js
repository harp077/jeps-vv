Vue.component('jeps-passgen-dialog', {
    template: `
        <v-dialog persistent hide-overlay v-model="passgendlg" width="450">
            <v-toolbar color="light-blue">
                <v-toolbar-title class="black--text">Password Generator</v-toolbar-title>
                <v-spacer></v-spacer>
                <v-btn icon color="yellow" v-on:click.native="setfalse">
                    <v-icon>cancel</v-icon>
                </v-btn>
            </v-toolbar>    
            <v-card light>
                <v-card-text>    
                    <v-container fill-height fluid>
                        <v-layout fill-height>
                            <v-flex align-end flexbox>
                                <v-switch color="green"
                                    v-bind:label="specCharLabel"
                                    v-model="useSpecialChar">
                                </v-switch> 
                                <v-select combobox chips dense dont-fill-mask-blanks
                                    cache-items
                                    v-bind:items="alfavites"
                                    v-model="alphabet"
                                    label="Select Alphabet:">
                                </v-select> 
                                <v-slider title="Number of Chars"
                                    step="1" ticks
                                    thumb-label
                                    v-model="charNum"
                                    v-bind:min="8"
                                    v-bind:max="24"
                                    v-bind:label="charNumLabel">
                                </v-slider>
                                <v-text-field color="green"
                                    label="Random generated password:"
                                    v-model="GeneratedPWD"
                                    v-on:keyup.enter="setGeneratedPWD">
                                </v-text-field>
                                <v-btn block dark color="green" v-on:click="setGeneratedPWD()">
                                    <v-icon>chevron_right</v-icon> &nbsp; Get Random Password
                                </v-btn>      
                            </v-flex>
                        </v-layout>
                    </v-container> 
                </v-card-text>
            </v-card>
        </v-dialog>    
    `,
    data: function(){
        return {
            charNum: 9,
            GeneratedPWD: {}, //JSON.parse('gen').pop(),            
            alphabet: 'aA-zZ_0-9',
            useSpecialChar: false,
            alfavites: ["A-Z_0-9", "aA-zZ_0-9", "a-z_0-9"],
            numberChars: [8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24]
        };
    },
    computed: {
        passgendlg() {
            return this.$store.getters.PassgenDialogState;
        },        
        specCharLabel () {
            return 'Use Special Chars: ' + this.useSpecialChar;
        },
        charNumLabel () {
            return 'Chars: ' + this.charNum;
        }        
    },
    methods: {
        setfalse() {
            this.$store.dispatch('changePassgenDialogState',false);
        },              
        setGeneratedPWD: function () {
            axios({
                method: 'get',
                url: '/rest/user/json',
                params: {
                    alfavit: this.alphabet,
                    specuse: this.useSpecialChar,
                    numchar: this.charNum
                }/*,
                 auth: {
                 //username: this.rslogin, 
                 //password: this.rspassw 
                 username: this.$root.$refs.jepsrestpwds.apiuser,
                 password: this.$root.$refs.jepsrestpwds.apipass
                 //username: 'rest', 
                 //password: 'rest'                                       
                 }*/
            })
                    // arg =>  === function(arg) !!! - стрелочные функции
                    .then(response => {
                        this.GeneratedPWD = response.data;
                    })
                    .catch(error => {
                        //this.restdata.errors = push(error);
                    });
        }        
    },
    created: function () {
        this.setGeneratedPWD();
    }    
});
