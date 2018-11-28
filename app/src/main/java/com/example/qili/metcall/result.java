package com.example.qili.metcall;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class result extends AppCompatActivity {

    private TextView View1;
    private TextView View2;
    private TextView View3;
    private TextView View4;
    private TextView View5;
    private Button button_result;
    private Button button_inputcci;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        View1 = (TextView) findViewById(R.id.CART);
        View2 = (TextView) findViewById(R.id.NB);
        View3 = (TextView) findViewById(R.id.RF);
        View4 = (TextView) findViewById(R.id.p_sug);
        View5 = (TextView) findViewById(R.id.hybrid);

        Intent intent = getIntent();
        int sex = intent.getIntExtra("gender", 0);
        int smoke = intent.getIntExtra("smoking", 0);
        int alcohol = intent.getIntExtra("alcohol", 0);
        int adm = intent.getIntExtra("admsourc", 0);
        int emnone = intent.getIntExtra("emnone", 0);
        int cci = intent.getIntExtra("cci_admit", 0);
        int threatre_mins = intent.getIntExtra("threatre_mins", 0);
        int anaesthetic = intent.getIntExtra("anaesthetic", 0);
        int age_triage = intent.getIntExtra("age", 0);


        String operations = intent.getStringExtra("operations");
        int OperID = 0;

        if (operations == "THR") {
            OperID = 1;
        } else {
            if (operations == "ORIF - DHS/NAIL") {
                OperID = 2;
            } else {
                if (operations == "ORIF - Femur") {
                    OperID = 3;
                } else {
                    if (operations == "Decompressiv laminctomy/spinal fusion") {
                        OperID = 4;
                    } else {
                        if (operations == "ORIF - Tibia/fibula/patella") {
                            OperID = 5;
                        } else {
                            if (operations == "TKR/UKR") {
                                OperID = 6;
                            } else {
                                if (operations == "Hemi-arthroplasty") {
                                    OperID = 7;
                                } else {
                                    if (operations == "Other") {
                                        OperID = 8;
                                    } else {
                                        if (operations == "Other UL surgery/shoulder other (incl. arthroscopy)/hand surgery") {
                                            OperID = 9;
                                        } else {
                                            if (operations == "Knee arthroscopy") {
                                                OperID = 10;
                                            } else {
                                                if (operations == "Shoulder/elbow arthroplasty") {
                                                    OperID = 11;
                                                } else {
                                                    if (operations == "Foot and ankle surgeruy") {
                                                        OperID = 12;
                                                    } else {
                                                        if (operations == "Hip Arthroscopy") {
                                                            OperID = 13;
                                                        } else {
                                                            if (operations == "Ligamentous/Meniscal surgery") {
                                                                OperID = 14;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        double probability_CART = 0;
        double probability_NB = 0;
        //probability_suggested  = rule_based probability
        double probability_suggested = 0;
        double probability_casebased = 0;
        double probability_final = 0 ;

        String rulebased_output;

        if (age_triage < 18 && age_triage > 0) {
            new AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("Age should less than 18, please click BACK button and input values again")
                    .setPositiveButton("Confirm", null)
                    .show();
        } else {
            if (cci > 22) {
                new AlertDialog.Builder(this)
                        .setTitle("Error")
                        .setMessage("Charlson Score should less than 22, please click BACK button and input values again")
                        .setPositiveButton("Confirm", null)
                        .show();
            } else {
                if ((age_triage >= 15 && age_triage < 20) || (age_triage >= 25 && age_triage < 75)) {
                    if (OperID == 12 || OperID == 10 || OperID == 14 || OperID == 5 || OperID == 9 || OperID == 1) {
                        probability_CART = 0.13;
                    } else {
                        if (cci < 4.5) {
                            if (adm == 0) {
                                probability_CART = 0.35;
                            } else {
                                probability_CART = 0.85;
                            }
                        } else {
                            probability_CART = 0.73;
                        }
                    }
                } else {
                    if (OperID == 12 || OperID == 13 || OperID == 10 || OperID == 14 || OperID == 5 || OperID == 9
                            || OperID == 11) {
                        probability_CART = 0.45;
                    } else {
                        probability_CART = 0.82;
                    }
                }

                /* Probability calculator of individual Naive Bayes model */
                if (age_triage >= 85 && age_triage < 90) {
                    probability_NB = 0.847;
                } else {
                    if (age_triage >= 70 && age_triage < 75) {
                        probability_NB = 0.247;
                    } else {
                        if (age_triage >= 80 && age_triage < 85) {
                            probability_NB = 0.797;
                        } else {
                            if (age_triage >= 40 && age_triage < 45) {
                                switch (alcohol) {
                                    case 0:
                                        probability_NB = 0.054;
                                        break;
                                    case 1:
                                        probability_NB = 0.8;
                                        break;
                                    case 2:
                                        probability_NB = 0;
                                        break;
                                }
                            } else {
                                if (age_triage >= 90 && age_triage < 95) {
                                    probability_NB = 0.849;
                                } else {
                                    if (age_triage >= 75 && age_triage < 80) {
                                        probability_NB = 0.754;
                                    } else {
                                        if (age_triage >= 60 && age_triage < 65) {
                                            switch (cci) {
                                                case 0:
                                                    probability_NB = 0;
                                                    break;
                                                case 1:
                                                    probability_NB = 0;
                                                    break;
                                                case 2:
                                                    probability_NB = 0.125;
                                                    break;
                                                case 3:
                                                    probability_NB = 0.239;
                                                    break;
                                                case 4:
                                                    probability_NB = 0.684;
                                                    break;
                                                case 5:
                                                    probability_NB = 0.286;
                                                    break;
                                                case 6:
                                                    probability_NB = 0.25;
                                                    break;
                                                case 7:
                                                    probability_NB = 0.857;
                                                    break;
                                                case 8:
                                                    probability_NB = 0.833;
                                                    break;
                                                case 9:
                                                    probability_NB = 1;
                                                    break;
                                                case 10:
                                                    probability_NB = 0;
                                                    break;
                                                case 11:
                                                    probability_NB = 0;
                                                    break;
                                                case 14:
                                                    probability_NB = 0;
                                                    break;
                                            }
                                        } else {
                                            if (age_triage >= 25 && age_triage < 30) {
                                                probability_NB = 0.924;
                                            } else {
                                                if (age_triage >= 20 && age_triage < 25)
                                                    switch (adm) {
                                                        case 0:
                                                            probability_NB = 0.04;
                                                            break;
                                                        case 1:
                                                            probability_NB = 1;
                                                            break;
                                                        case 2:
                                                            probability_NB = 0.935;
                                                            break;
                                                        case 3:
                                                            probability_NB = 1;
                                                            break;
                                                    }
                                                else {
                                                    if (age_triage >= 65 && age_triage < 70) {
                                                        probability_NB = 0.268;
                                                    } else {
                                                        if (age_triage >= 55 && age_triage < 60) {
                                                            switch (OperID) {
                                                                case 2:
                                                                    probability_NB = 0.750;
                                                                    break;
                                                                case 3:
                                                                    probability_NB = 0.008;
                                                                    break;
                                                                case 8:
                                                                    switch (anaesthetic) {
                                                                        case 0:
                                                                            probability_NB = 0.071;
                                                                            break;
                                                                        case 1:
                                                                            probability_NB = 0.765;
                                                                            break;
                                                                        case 2:
                                                                            probability_NB = 0;
                                                                            break;
                                                                        case 3:
                                                                            probability_NB = 0;
                                                                            break;
                                                                        case 4:
                                                                            probability_NB = 0;
                                                                            break;
                                                                    }
                                                                    break;
                                                                case 5:
                                                                    switch (smoke) {
                                                                        case 0:
                                                                            probability_NB = 0.145;
                                                                            break;
                                                                        case 1:
                                                                            switch (sex) {
                                                                                case 0:
                                                                                    probability_NB = 1;
                                                                                    break;
                                                                                case 1:
                                                                                    probability_NB = 0;
                                                                                    break;
                                                                            }
                                                                            break;
                                                                        case 2:
                                                                            probability_NB = 0;
                                                                            break;
                                                                    }
                                                                    break;
                                                                case 7:
                                                                    probability_NB = 0.014;
                                                                    break;
                                                                case 6:
                                                                    switch (anaesthetic) {
                                                                        case 0:
                                                                            probability_NB = 0.012;
                                                                            break;
                                                                        case 1:
                                                                            probability_NB = 0.767;
                                                                            break;
                                                                        case 2:
                                                                            probability_NB = 0;
                                                                            break;
                                                                        case 3:
                                                                            probability_NB = 0;
                                                                            break;
                                                                        case 4:
                                                                            probability_NB = 0;
                                                                            break;
                                                                    }
                                                                    break;
                                                                case 4:
                                                                    switch (smoke) {
                                                                        case 0:
                                                                            probability_NB = 0.280;
                                                                            break;
                                                                        case 1:
                                                                            probability_NB = 0.944;
                                                                            break;
                                                                        case 2:
                                                                            probability_NB = 0;
                                                                            break;
                                                                    }
                                                                    break;
                                                                case 1:
                                                                    switch (anaesthetic) {
                                                                        case 0:
                                                                            switch (sex) {
                                                                                case 0:
                                                                                    probability_NB = 0.061;
                                                                                    break;
                                                                                case 1:
                                                                                    probability_NB = 0.777;
                                                                                    break;
                                                                            }
                                                                            break;
                                                                        case 1:
                                                                            probability_NB = 0.013;
                                                                            break;
                                                                        case 2:
                                                                            probability_NB = 0;
                                                                            break;
                                                                        case 3:
                                                                            probability_NB = 0;
                                                                            break;
                                                                        case 4:
                                                                            probability_NB = 0;
                                                                            break;
                                                                    }
                                                                    break;
                                                                case 11:
                                                                    probability_NB = 0;
                                                                    break;
                                                                case 9:
                                                                    probability_NB = 0.013;
                                                                    break;
                                                                case 12:
                                                                    probability_NB = 0.013;
                                                                    break;
                                                                case 10:
                                                                    probability_NB = 0.011;
                                                                    break;
                                                                case 13:
                                                                    probability_NB = 0.009;
                                                                    break;
                                                                case 14:
                                                                    probability_NB = 0.009;
                                                                    break;
                                                            }
                                                        } else {
                                                            if (age_triage >= 50 && age_triage < 55) {
                                                                probability_NB = 0.151;
                                                            } else {
                                                                if (age_triage >= 30 && age_triage < 35) {
                                                                    probability_NB = 0.179;
                                                                } else {
                                                                    if (age_triage >= 95 && age_triage < 100) {
                                                                        probability_NB = 0.732;
                                                                    } else {
                                                                        if (age_triage >= 45 && age_triage < 50) {
                                                                            probability_NB = 0.132;
                                                                        } else {
                                                                            if (age_triage >= 35 && age_triage < 40) {
                                                                                probability_NB = 0.138;
                                                                            } else {
                                                                                if (age_triage >= 100) {
                                                                                    probability_NB = 1;
                                                                                } else {
                                                                                    if (age_triage >= 15 && age_triage < 20) {
                                                                                        probability_NB = 0;
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                switch (age_triage) {
                    case 20:
                        if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 1 && cci == 0 && anaesthetic == 0) {
                            probability_casebased = 1.00;
                        } else {
                            if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 8 && cci == 0 && anaesthetic == 0) {
                                probability_casebased = 1.00;
                            } else {
                                probability_casebased = 0.00;
                            }
                        }
                        break;

                    case 21:
                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 5 && cci == 0 && anaesthetic == 0) {
                            probability_casebased = 0.33;
                        } else {
                            if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 8 && cci == 0 && anaesthetic == 0) {
                                probability_casebased = 1.00;
                            } else {
                                probability_casebased = 0.00;
                            }
                        }
                        break;

                    case 24:
                        if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 0 && adm == 2 && OperID == 8 && cci == 0 && anaesthetic == 0) {
                            probability_casebased = 1.00;
                        } else {
                            probability_casebased = 0.00;
                        }
                        break;

                    case 25:
                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 8 && cci == 0 && anaesthetic == 0) {
                            probability_casebased = 1.00;
                        } else {
                            if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 12 && cci == 0 && anaesthetic == 0) {
                                probability_casebased = 0.5;
                            }
                        }
                        break;

                    case 27:
                        if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 0 && adm == 2 && OperID == 12 && cci == 0 && anaesthetic == 0) {
                            probability_casebased = 1.00;
                        } else {
                            probability_casebased = 0.00;
                        }
                        break;

                    case 28:
                        if (sex == 0 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && cci == 1) {
                            probability_casebased = 1.00;
                        } else {
                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 2 && OperID == 12 && cci == 2 && anaesthetic == 0) {
                                probability_casebased = 1.00;
                            } else {
                                probability_casebased = 0.00;
                            }
                        }
                        break;

                    case 29:
                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 2 && OperID == 4 && cci == 0 && anaesthetic == 0) {
                            probability_casebased = 1.00;
                        } else {
                            if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 4 && cci == 0 && anaesthetic == 0) {
                                probability_casebased = 1.00;
                            } else {
                                probability_casebased = 0.00;
                            }
                        }
                        break;

                    case 31:
                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 2 && OperID == 5 && cci == 1 && anaesthetic == 0) {
                            probability_casebased = 1.00;
                        } else {
                            probability_casebased = 1.00;
                        }
                        break;

                    case 32:
                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 8 && cci == 0 && anaesthetic == 0) {
                            probability_casebased = 0.5;
                        } else {
                            probability_casebased = 0.00;
                        }
                        break;

                    case 33:
                        if (sex == 0 && smoke == 1 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 13 && cci == 0 && anaesthetic == 0) {
                            probability_casebased = 1.00;
                        } else {
                            probability_casebased = 0.00;
                        }
                        break;

                    case 34:
                        if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 8 && cci == 0 && anaesthetic == 0) {
                            probability_casebased = 1.00;
                        } else {
                            probability_casebased = 0.00;
                        }
                        break;

                    case 35:
                        if (sex == 1 && smoke == 1 && alcohol == 2 && emnone == 1 && adm == 0 && OperID == 9 && cci == 1 && anaesthetic == 0) {
                            probability_casebased = 1.00;
                        } else {
                            probability_casebased = 0.00;
                        }
                        break;

                    case 36:
                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 2 && OperID == 4 && cci == 0 && anaesthetic == 0) {
                            probability_casebased = 1.00;
                        } else {
                            probability_casebased = 0.00;
                        }
                        break;

                    case 37:
                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 5 && cci == 3 && anaesthetic == 0) {
                            probability_casebased = 1.00;
                        } else {
                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 8 && cci == 0 && anaesthetic == 0) {
                                probability_casebased = 1.00;
                            } else {
                                if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 4 && cci == 2 && anaesthetic == 0) {
                                    probability_casebased = 1.00;
                                } else {
                                    probability_casebased = 0.00;
                                }
                            }
                        }
                        break;

                    case 39:
                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 1 && cci == 0 && anaesthetic == 0) {
                            probability_casebased = 1.00;
                        } else {
                            probability_casebased = 0.00;
                        }
                        break;

                    case 41:
                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 4 && cci == 1 && anaesthetic == 4) {
                            probability_casebased = 0.33;
                        } else {
                            if (sex == 0 && smoke == 1 && alcohol == 2 && emnone == 1 && adm == 0 && OperID == 5 && cci == 3 && anaesthetic == 0) {
                                probability_casebased = 1.00;
                            } else {
                                probability_casebased = 0.00;
                            }
                        }
                        break;

                    case 43:
                        if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 0 && adm == 2 && cci == 3 && anaesthetic == 0) {
                            probability_casebased = 1.00;
                        } else {
                            probability_casebased = 0.00;
                        }
                        break;

                    case 44:
                        if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && cci == 7) {
                            probability_casebased = 1.00;
                        } else {
                            probability_casebased = 0.00;
                        }
                        break;

                    case 45:
                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 2 && anaesthetic == 0) {
                            probability_casebased = 1.00;
                        } else {
                            if (sex == 0 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 11 && cci == 1 && anaesthetic == 0) {
                                probability_casebased = 1.00;
                            } else {
                                probability_casebased = 0.00;
                            }
                        }
                        break;

                    case 47:
                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 8 && cci == 2 && anaesthetic == 1) {
                            probability_casebased = 1.00;
                        } else {
                            probability_casebased = 0.00;
                        }
                        break;

                    case 48:
                        if (sex == 0 && smoke == 1 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 4 && cci == 2 && anaesthetic == 0) {
                            probability_casebased = 1.00;
                        } else {
                            if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 1 && cci == 1 && anaesthetic == 1) {
                                probability_casebased = 0.5;
                            } else {
                                probability_casebased = 0.00;
                            }
                        }
                        break;

                    case 49:
                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 4 && cci == 1 && anaesthetic == 0) {
                            probability_casebased = 0.5;
                        } else {
                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 1 && cci == 1 && anaesthetic == 0) {
                                probability_casebased = 0.5;
                            }
                        }
                        break;

                    case 50:
                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 2 && cci == 1 && anaesthetic == 0) {
                            probability_casebased = 1.00;
                        } else {
                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 2 && OperID == 4 && cci == 2 && anaesthetic == 0) {
                                probability_casebased = 1.00;
                            } else {
                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 1 && cci == 1 && anaesthetic == 0) {
                                    probability_casebased = 1.00;
                                } else {
                                    if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 8 && cci == 1 && anaesthetic == 0) {
                                        probability_casebased = 1.00;
                                    } else {
                                        probability_casebased = 0.00;
                                    }
                                }
                            }
                        }
                        break;

                    case 51:
                        if (sex == 0 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 1 && cci == 2 && anaesthetic == 1) {
                            probability_casebased = 1.00;
                        } else {
                            probability_casebased = 0.00;
                        }
                        break;

                    case 52:
                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 1 && cci == 2 && anaesthetic == 0) {
                            probability_casebased = 1.00;
                        } else {
                            if (sex == 1 && smoke == 0 && alcohol == 2 && emnone == 1 && adm == 0 && OperID == 2 && cci == 2 && anaesthetic == 1) {
                                probability_casebased = 1.00;
                            } else {
                                if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 4 && cci == 2 && anaesthetic == 0) {
                                    probability_casebased = 0.2;
                                }
                            }
                        }
                        break;

                    case 53:
                        if (sex == 0 && smoke == 1 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 4 && cci == 2 && anaesthetic == 0) {
                            probability_casebased = 0.2;
                        } else {
                            if (sex == 0 && smoke == 1 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 4 && cci == 3 && anaesthetic == 0) {
                                probability_casebased = 1.00;
                            } else {
                                if (sex == 0 && smoke == 1 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 9 && cci == 2 && anaesthetic == 0) {
                                    probability_casebased = 0.33;
                                } else {
                                    if (sex == 0 && smoke == 1 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 1 && cci == 3 && anaesthetic == 0) {
                                        probability_casebased = 1.00;
                                    } else {
                                        if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 12 && cci == 2 && anaesthetic == 0) {
                                            probability_casebased = 1.00;
                                        } else {
                                            probability_casebased = 0.00;
                                        }
                                    }
                                }
                            }
                        }
                        break;

                    case 54:
                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 5 && cci == 2 && anaesthetic == 0) {
                            probability_casebased = 0.2;
                        } else {
                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 1 && cci == 2 && anaesthetic == 0) {
                                probability_casebased = 1.00;
                            } else {
                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 4 && cci == 2 && anaesthetic == 0) {
                                    probability_casebased = 0.33;
                                } else {
                                    if (sex == 1 && smoke == 0 && alcohol == 2 && emnone == 1 && adm == 0 && OperID == 9 && cci == 2 && anaesthetic == 0) {
                                        probability_casebased = 1.00;
                                    } else {
                                        if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 1 && cci == 3 && anaesthetic == 0) {
                                            probability_casebased = 1.00;
                                        } else {
                                            probability_casebased = 0.00;
                                        }
                                    }
                                }
                            }
                        }
                        break;

                    case 55:
                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && cci == 3) {
                            probability_casebased = 1.00;
                        } else {
                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 4 && cci == 2 && anaesthetic == 0) {
                                probability_casebased = 0.2;
                            } else {
                                if (sex == 0 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 4 && cci == 8 && anaesthetic == 0) {
                                    probability_casebased = 1.00;
                                } else {
                                    if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 1 && cci == 8 && anaesthetic == 0) {
                                        probability_casebased = 1.00;
                                    } else {
                                        if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && cci == 2) {
                                            probability_casebased = 1.00;
                                        } else {
                                            probability_casebased = 0.00;
                                        }
                                    }
                                }
                            }
                        }
                        break;

                    case 56:
                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 1 && OperID == 8 && cci == 5 && anaesthetic == 0) {
                            probability_casebased = 1.00;
                        } else {
                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 1 && cci == 2 && anaesthetic == 0) {
                                probability_casebased = 1.00;
                            } else {
                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 8 && cci == 2 && anaesthetic == 4) {
                                    probability_casebased = 1.00;
                                } else {
                                    if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 1 && OperID == 8 && cci == 5 && anaesthetic == 1) {
                                        probability_casebased = 1.00;
                                    } else {
                                        if (sex == 0 && smoke == 1 && alcohol == 2 && emnone == 1 && adm == 0 && OperID == 2 && cci == 3 && anaesthetic == 0) {
                                            probability_casebased = 1.00;
                                        } else {
                                            if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 5 && cci == 3 && anaesthetic == 0) {
                                                probability_casebased = 1.00;
                                            } else {
                                                if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 2 && anaesthetic == 0) {
                                                    probability_casebased = 1.00;
                                                } else {
                                                    if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 2 && OperID == 4 && cci == 9 && anaesthetic == 0) {
                                                        probability_casebased = 1.00;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;

                    case 57:
                        if (sex == 0 && smoke == 2 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 1 && cci == 2 && anaesthetic == 1) {
                            probability_casebased = 1.00;
                        } else {
                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 4 && cci == 4 && anaesthetic == 0) {
                                probability_casebased = 1.00;
                            } else {
                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 5 && cci == 2 && anaesthetic == 0) {
                                    probability_casebased = 0.25;
                                } else {
                                    if (sex == 0 && smoke == 1 && alcohol == 2 && emnone == 1 && adm == 0 && OperID == 5 && cci == 3 && anaesthetic == 0) {
                                        probability_casebased = 1.00;
                                    } else {
                                        if (sex == 0 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 4 && anaesthetic == 0) {
                                            probability_casebased = 1.00;
                                        } else {
                                            if (sex == 1 && smoke == 2 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 2 && anaesthetic == 3) {
                                                probability_casebased = 1.00;
                                            } else {
                                                if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 4 && cci == 2 && anaesthetic == 0) {
                                                    probability_casebased = 1.00;
                                                } else {
                                                    if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 4 && cci == 2 && anaesthetic == 0) {
                                                        probability_casebased = 0.5;
                                                    } else {
                                                        probability_casebased = 0.00;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;

                    case 58:
                        if (sex == 0 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 4 && cci == 2 && anaesthetic == 0) {
                            probability_casebased = 0.5;
                        } else {
                            if (sex == 0 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 5 && cci == 2 && anaesthetic == 0) {
                                probability_casebased = 0.5;
                            } else {
                                if (sex == 0 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 8 && cci == 2 && anaesthetic == 1) {
                                    probability_casebased = 1.00;
                                } else {
                                    if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 3 && anaesthetic == 0) {
                                        probability_casebased = 1.00;
                                    } else {
                                        if (sex == 1 && smoke == 1 && alcohol == 2 && emnone == 1 && adm == 0 && OperID == 4 && cci == 4 && anaesthetic == 0) {
                                            probability_casebased = 1.00;
                                        } else {
                                            if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 1 && OperID == 1 && cci == 2 && anaesthetic == 0) {
                                                probability_casebased = 1.00;
                                            } else {
                                                probability_casebased = 0.00;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;

                    case 59:
                        if (sex == 0 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 6 && cci == 4 && anaesthetic == 1) {
                            probability_casebased = 1.00;
                        } else {
                            if (sex == 1 && smoke == 2 && alcohol == 2 && emnone == 1 && adm == 0 && OperID == 1 && cci == 4 && anaesthetic == 0) {
                                probability_casebased = 1.00;
                            } else {
                                if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 2 && anaesthetic == 0) {
                                    probability_casebased = 1.00;
                                } else {
                                    if (sex == 0 && smoke == 2 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 8 && cci == 8 && anaesthetic == 0) {
                                        probability_casebased = 1.00;
                                    } else {
                                        if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 2 && anaesthetic == 0) {
                                            probability_casebased = 0.5;
                                        } else {
                                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 2 && OperID == 4 && cci == 2 && anaesthetic == 0) {
                                                probability_casebased = 1.00;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;

                    case 60:
                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 9 && cci == 4 && anaesthetic == 0) {
                            probability_casebased = 1.00;
                        } else {
                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 2 && OperID == 4 && cci == 4 && anaesthetic == 0) {
                                probability_casebased = 1.00;
                            } else {
                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 4 && cci == 2 && anaesthetic == 0) {
                                    probability_casebased = 0.5;
                                } else {
                                    probability_casebased = 0.00;
                                }
                            }
                        }
                        break;

                    case 61:
                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 6 && cci == 3 && anaesthetic == 0) {
                            probability_casebased = 1.00;
                        } else {
                            if (sex == 0 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 3 && cci == 9 && anaesthetic == 2) {
                                probability_casebased = 1.00;
                            } else {
                                if (sex == 0 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 6 && cci == 5 && anaesthetic == 1) {
                                    probability_casebased = 1.00;
                                } else {
                                    if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 4 && cci == 1 && anaesthetic == 4) {
                                        probability_casebased = 1.00;
                                    } else {
                                        if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 11 && cci == 3 && anaesthetic == 0) {
                                            probability_casebased = 1.00;
                                        } else {
                                            if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 1 && cci == 6 && anaesthetic == 0) {
                                                probability_casebased = 1.00;
                                            } else {
                                                if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && cci == 3) {
                                                    probability_casebased = 0.5;
                                                } else {
                                                    if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 3 && anaesthetic == 0) {
                                                        probability_casebased = 1.00;
                                                    } else {
                                                        if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 6 && cci == 7 && anaesthetic == 0) {
                                                            probability_casebased = 1.00;
                                                        } else {
                                                            probability_casebased = 0.00;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;

                    case 62:
                        if (sex == 0 && smoke == 2 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 11 && cci == 7 && anaesthetic == 0) {
                            probability_casebased = 1.00;
                        } else {
                            if (sex == 0 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 3 && anaesthetic == 0) {
                                probability_casebased = 1.00;
                            } else {
                                if (sex == 0 && smoke == 1 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 4 && cci == 3 && anaesthetic == 0) {
                                    probability_casebased = 0.33;
                                } else {
                                    if (sex == 0 && smoke == 1 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 1 && cci == 3 && anaesthetic == 0) {
                                        probability_casebased = 0.5;
                                    } else {
                                        if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 1 && OperID == 7 && cci == 4 && anaesthetic == 0) {
                                            probability_casebased = 1.00;
                                        } else {
                                            if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 0 && cci == 9) {
                                                probability_casebased = 1.00;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;

                    case 63:
                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 4 && cci == 3 && anaesthetic == 0) {
                            probability_casebased = 0.25;
                        } else {
                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 4 && cci == 4 && anaesthetic == 0) {
                                probability_casebased = 1.00;
                            } else {
                                if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 8 && cci == 3 && anaesthetic == 0) {
                                    probability_casebased = 0.33;
                                } else {
                                    if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 6 && cci == 3 && anaesthetic == 0) {
                                        probability_casebased = 1.00;
                                    } else {
                                        probability_casebased = 0.00;
                                    }
                                }
                            }
                        }
                        break;

                    case 64:
                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 1 && cci == 3 && anaesthetic == 0) {
                            probability_casebased = 1.00;
                        } else {
                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 1 && cci == 3 && anaesthetic == 1) {
                                probability_casebased = 0.33;
                            } else {
                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 1 && OperID == 1 && cci == 3 && anaesthetic == 0) {
                                    probability_casebased = 1.00;
                                } else {
                                    if (sex == 1 && smoke == 1 && alcohol == 2 && emnone == 1 && adm == 0 && OperID == 5 && cci == 5 && anaesthetic == 1) {
                                        probability_casebased = 1.00;
                                    }
                                }
                            }
                        }
                        break;

                    case 65:
                        if (sex == 0 && smoke == 2 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 3 && anaesthetic == 1) {
                            probability_casebased = 1.00;
                        } else {
                            if (sex == 0 && smoke == 0 && alcohol == 2 && emnone == 1 && adm == 0 && cci == 3 && anaesthetic == 0) {
                                probability_casebased = 1.00;
                            } else {
                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 3 && cci == 3 && anaesthetic == 0) {
                                    probability_casebased = 1.00;
                                } else {
                                    if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 9 && cci == 3 && anaesthetic == 0) {
                                        probability_casebased = 0.125;
                                    } else {
                                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 9 && cci == 1 && anaesthetic == 0) {
                                            probability_casebased = 1.00;
                                        } else {
                                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 8 && cci == 3 && anaesthetic == 0) {
                                                probability_casebased = 0.5;
                                            } else {
                                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 1 && cci == 6 && anaesthetic == 0) {
                                                    probability_casebased = 1.00;
                                                } else {
                                                    if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 6 && cci == 5 && anaesthetic == 1) {
                                                        probability_casebased = 0.5;
                                                    } else {
                                                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 2 && cci == 9 && anaesthetic == 0) {
                                                            probability_casebased = 1.00;
                                                        } else {
                                                            if (sex == 0 && smoke == 1 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 1 && cci == 3 && anaesthetic == 0) {
                                                                probability_casebased = 0.2;
                                                            } else {
                                                                if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 1 && cci == 4 && anaesthetic == 1) {
                                                                    probability_casebased = 1.00;
                                                                } else {
                                                                    if (sex == 1 && smoke == 2 && alcohol == 0 && emnone == 0 && adm == 2 && OperID == 8 && cci == 3 && anaesthetic == 0) {
                                                                        probability_casebased = 1.00;
                                                                    } else {
                                                                        if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 1 && cci == 3 && anaesthetic == 0) {
                                                                            probability_casebased = 1.00;
                                                                        } else {
                                                                            if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 5 && cci == 3 && anaesthetic == 0) {
                                                                                probability_casebased = 0.5;
                                                                            } else {
                                                                                probability_casebased = 0.00;
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;

                    case 66:
                        if (sex == 0 && smoke == 2 && alcohol == 0 && emnone == 1 && adm == 0 && cci == 4) {
                            probability_casebased = 1.00;
                        } else {
                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 4 && cci == 5 && anaesthetic == 0) {
                                probability_casebased = 1.00;
                            } else {
                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 12 && cci == 3 && anaesthetic == 0) {
                                    probability_casebased = 1.00;
                                } else {
                                    if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 11 && cci == 5 && anaesthetic == 0) {
                                        probability_casebased = 1.00;
                                    } else {
                                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 4 && cci == 3 && anaesthetic == 0) {
                                            probability_casebased = 1.00;
                                        } else {
                                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 11 && cci == 4 && anaesthetic == 0) {
                                                probability_casebased = 1.00;
                                            } else {
                                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 6 && cci == 3 && anaesthetic == 0) {
                                                    probability_casebased = 0.33;
                                                } else {
                                                    if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 2 && OperID == 4 && cci == 6 && anaesthetic == 0) {
                                                        probability_casebased = 1.00;
                                                    } else {
                                                        if (sex == 1 && smoke == 2 && alcohol == 2 && emnone == 1 && adm == 0 && cci == 3 && anaesthetic == 2) {
                                                            probability_casebased = 1.00;
                                                        } else {
                                                            if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 6 && cci == 3 && anaesthetic == 0) {
                                                                probability_casebased = 1.00;
                                                            } else {
                                                                probability_casebased = 0.00;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;

                    case 67:
                        if (sex == 0 && smoke == 2 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 11 && cci == 5 && anaesthetic == 0) {
                            probability_casebased = 1.00;
                        } else {
                            if (sex == 0 && smoke == 2 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 9 && cci == 4 && anaesthetic == 0) {
                                probability_casebased = 1.00;
                            } else {
                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 4 && anaesthetic == 0) {
                                    probability_casebased = 0.5;
                                } else {
                                    if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && cci == 4) {
                                        probability_casebased = 0.67;
                                    } else {
                                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 4 && cci == 3 && anaesthetic == 0) {
                                            probability_casebased = 0.25;
                                        } else {
                                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 8 && cci == 3 && anaesthetic == 1) {
                                                probability_casebased = 1.00;
                                            } else {
                                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 6 && cci == 3 && anaesthetic == 0) {
                                                    probability_casebased = 0.66;
                                                } else {
                                                    if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 2 && cci == 7) {
                                                        probability_casebased = 1.00;
                                                    } else {
                                                        if (sex == 0 && smoke == 1 && alcohol == 1 && emnone == 1 && adm == 0 && OperID == 7 && cci == 7 && anaesthetic == 3) {
                                                            probability_casebased = 1.00;
                                                        } else if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 1 && cci == 3 && anaesthetic == 0) {
                                                            probability_casebased = 0.33;
                                                        } else {
                                                            if (sex == 1 && smoke == 1 && alcohol == 2 && emnone == 1 && adm == 0 && OperID == 7 && cci == 3 && anaesthetic == 0) {
                                                                probability_casebased = 1.00;
                                                            } else {
                                                                probability_casebased = 0.00;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;

                    case 68:
                        if (sex == 0 && smoke == 0 && alcohol == 2 && emnone == 1 && adm == 0 && OperID == 6 && cci == 3 && anaesthetic == 0) {
                            probability_casebased = 1.00;
                        } else {
                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 3 && cci == 3 && anaesthetic == 0) {
                                probability_casebased = 1.00;
                            } else {
                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 2 && OperID == 2 && cci == 4 && anaesthetic == 0) {
                                    probability_casebased = 1.00;
                                } else {
                                    if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 5 && cci == 4 && anaesthetic == 0) {
                                        probability_casebased = 1.00;
                                    } else {
                                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 6 && cci == 3 && anaesthetic == 0) {
                                            probability_casebased = 0.2;
                                        } else {
                                            if (sex == 0 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 4 && cci == 3 && anaesthetic == 0) {
                                                probability_casebased = 0.33;
                                            } else {
                                                if (sex == 0 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 6 && cci == 3 && anaesthetic == 0) {
                                                    probability_casebased = 0.5;
                                                } else {
                                                    if (sex == 0 && smoke == 2 && alcohol == 0 && emnone == 1 && adm == 2 && cci == 4 && anaesthetic == 0) {
                                                        probability_casebased = 1.00;
                                                    } else {
                                                        if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 3 && cci == 4 && anaesthetic == 0) {
                                                            probability_casebased = 1.00;
                                                        } else {
                                                            if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 6 && cci == 7 && anaesthetic == 0) {
                                                                probability_casebased = 1.00;
                                                            } else {
                                                                if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 6 && cci == 7 && anaesthetic == 1) {
                                                                    probability_casebased = 1.00;
                                                                } else {
                                                                    probability_casebased = 0.00;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;

                    case 69:
                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 8 && cci == 3 && anaesthetic == 0) {
                            probability_casebased = 0.5;
                        } else {
                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 9 && cci == 3) {
                                probability_casebased = 1.00;
                            } else {
                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 11 && cci == 3 && anaesthetic == 0) {
                                    probability_casebased = 1.00;
                                } else {
                                    if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 4 && cci == 3 && anaesthetic == 0) {
                                        probability_casebased = 0.33;
                                    } else {
                                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 11 && cci == 1 && anaesthetic == 0) {
                                            probability_casebased = 1.00;
                                        } else {
                                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 1 && cci == 1 && anaesthetic == 0) {
                                                probability_casebased = 1.00;
                                            } else {
                                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 6 && cci == 5 && anaesthetic == 1) {
                                                    probability_casebased = 0.33;
                                                } else {
                                                    if (sex == 0 && smoke == 1 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 6 && cci == 5 && anaesthetic == 0) {
                                                        probability_casebased = 1.00;
                                                    } else {
                                                        if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && cci == 6) {
                                                            probability_casebased = 1.00;
                                                        } else {
                                                            if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 6 && cci == 3 && anaesthetic == 1) {
                                                                probability_casebased = 0.5;
                                                            } else {
                                                                if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 2 && cci == 1) {
                                                                    probability_casebased = 0.5;
                                                                } else {
                                                                    if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 4 && cci == 5 && anaesthetic == 0) {
                                                                        probability_casebased = 1.00;
                                                                    } else {
                                                                        if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 6 && cci == 3 && anaesthetic == 1) {
                                                                            probability_casebased = 0.5;
                                                                        } else {
                                                                            probability_casebased = 0.00;
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;

                    case 70:
                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 5 && anaesthetic == 0) {
                            probability_casebased = 1.00;
                        } else {
                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 3 && cci == 5 && anaesthetic == 0) {
                                probability_casebased = 1.00;
                            } else {
                                if (sex == 0 && smoke == 1 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 11 && cci == 5 && anaesthetic == 0) {
                                    probability_casebased = 1.00;
                                } else {
                                    if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 10 && cci == 7 && anaesthetic == 0) {
                                        probability_casebased = 1.00;
                                    } else {
                                        if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && cci == 4) {
                                            probability_casebased = 0.33;
                                        } else {
                                            if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && cci == 3) {
                                                probability_casebased = 1.00;
                                            } else {
                                                if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 4 && cci == 9 && anaesthetic == 0) {
                                                    probability_casebased = 1.00;
                                                } else {
                                                    probability_casebased = 0.00;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;

                    case 71:
                        if (sex == 0 && smoke == 2 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 4 && anaesthetic == 0) {
                            probability_casebased = 1.00;
                        } else {
                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 1 && cci == 4 && anaesthetic == 1) {
                                probability_casebased = 1.00;
                            } else {
                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 9 && cci == 4 && anaesthetic == 0) {
                                    probability_casebased = 0.25;
                                } else {
                                    if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 6 && cci == 4 && anaesthetic == 0) {
                                        probability_casebased = 0.125;
                                    } else {
                                        if (sex == 0 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 1 && cci == 4 && anaesthetic == 0) {
                                            probability_casebased = 1.00;
                                        } else {
                                            if (sex == 1 && smoke == 2 && alcohol == 2 && emnone == 1 && adm == 0 && OperID == 1 && cci == 7 && anaesthetic == 0) {
                                                probability_casebased = 1.00;
                                            } else {
                                                if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 9 && cci == 4 && anaesthetic == 0) {
                                                    probability_casebased = 1.00;
                                                } else {
                                                    if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && cci == 8) {
                                                        probability_casebased = 1.00;
                                                    } else {
                                                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 1 && cci == 4 && anaesthetic == 0) {
                                                            probability_casebased = 1.00;
                                                        } else {
                                                            if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 4 && cci == 6 && anaesthetic == 0) {
                                                                probability_casebased = 1.00;
                                                            } else {
                                                                if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 1 && cci == 4 && anaesthetic == 0) {
                                                                    probability_casebased = 0.5;
                                                                } else {
                                                                    if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 6 && cci == 5 && anaesthetic == 0) {
                                                                        probability_casebased = 0.5;
                                                                    } else {
                                                                        probability_casebased = 0.5;
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;

                    case 72:
                        if (sex == 0 && smoke == 2 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 1 && cci == 4 && anaesthetic == 0) {
                            probability_casebased = 1.00;
                        } else {
                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 5 && anaesthetic == 0) {
                                probability_casebased = 0.5;
                            } else {
                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 8 && anaesthetic == 0) {
                                    probability_casebased = 1.00;
                                } else {
                                    if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 5 && cci == 4 && anaesthetic == 0) {
                                        probability_casebased = 0.429;
                                    } else {
                                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 9 && cci == 4 && anaesthetic == 0) {
                                            probability_casebased = 0.5;
                                        } else {
                                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 8 && anaesthetic == 0) {
                                                probability_casebased = 1.00;
                                            } else {
                                                if (sex == 0 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 1 && cci == 4 && anaesthetic == 0) {
                                                    probability_casebased = 1.00;
                                                } else {
                                                    if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 8 && anaesthetic == 0) {
                                                        probability_casebased = 1.00;
                                                    } else {
                                                        if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 4 && anaesthetic == 0) {
                                                            probability_casebased = 1.00;
                                                        } else {
                                                            if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 4 && cci == 7 && anaesthetic == 0) {
                                                                probability_casebased = 1.00;
                                                            } else {
                                                                probability_casebased = 0.00;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;

                    case 73:
                        if (sex == 0 && smoke == 2 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 6 && cci == 5 && anaesthetic == 1) {
                            probability_casebased = 1.00;
                        } else {
                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 1 && cci == 4 && anaesthetic == 0) {
                                probability_casebased = 1.00;
                            } else {
                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 6 && cci == 4 && anaesthetic == 0) {
                                    probability_casebased = 0.33;
                                } else {
                                    if (sex == 0 && smoke == 1 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 1 && cci == 4 && anaesthetic == 0) {
                                        probability_casebased = 0.33;
                                    } else {
                                        if (sex == 0 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 6 && cci == 4 && anaesthetic == 1) {
                                            probability_casebased = 1.00;
                                        } else {
                                            if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 10 && cci == 4 && anaesthetic == 0) {
                                                probability_casebased = 1.00;
                                            } else {
                                                if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 1 && cci == 5 && anaesthetic == 1) {
                                                    probability_casebased = 1.00;
                                                } else {
                                                    if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 1 && cci == 6 && anaesthetic == 0) {
                                                        probability_casebased = 1.00;
                                                    } else {
                                                        if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 2 && cci == 4) {
                                                            probability_casebased = 1.00;
                                                        } else {
                                                            probability_casebased = 0.00;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;

                    case 74:
                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 12 && cci == 6 && anaesthetic == 0) {
                            probability_casebased = 1.00;
                        } else {
                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 4 && anaesthetic == 0) {
                                probability_casebased = 1.00;
                            } else {
                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 6 && cci == 6 && anaesthetic == 0) {
                                    probability_casebased = 0.5;
                                } else {
                                    if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 2 && OperID == 4 && cci == 7 && anaesthetic == 0) {
                                        probability_casebased = 1.00;
                                    } else {
                                        if (sex == 0 && smoke == 1 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 4 && cci == 4 && anaesthetic == 0) {
                                            probability_casebased = 0.33;
                                        } else {
                                            if (sex == 0 && smoke == 1 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 6 && cci == 7 && anaesthetic == 1) {
                                                probability_casebased = 1.00;
                                            } else {
                                                if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 5 && cci == 4 && anaesthetic == 0) {
                                                    probability_casebased = 1.00;
                                                } else {
                                                    if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 1 && cci == 4 && anaesthetic == 0) {
                                                        probability_casebased = 1.00;
                                                    } else {
                                                        probability_casebased = 0.00;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;

                    case 75:
                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 4 && cci == 4 && anaesthetic == 0) {
                            probability_casebased = 1.00;
                        } else {
                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 4 && anaesthetic == 0) {
                                probability_casebased = 1.00;
                            } else {
                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 3 && cci == 8 && anaesthetic == 0) {
                                    probability_casebased = 1.00;
                                } else {
                                    if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 1 && cci == 4 && anaesthetic == 1) {
                                        probability_casebased = 1.00;
                                    } else {
                                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 4 && cci == 5 && anaesthetic == 0) {
                                            probability_casebased = 0.5;
                                        } else {
                                            if (sex == 0 && smoke == 1 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 1 && cci == 4 && anaesthetic == 1) {
                                                probability_casebased = 1.00;
                                            } else {
                                                if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 9 && anaesthetic == 0) {
                                                    probability_casebased = 1.00;
                                                } else {
                                                    if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 1 && OperID == 4 && cci == 4 && anaesthetic == 0) {
                                                        probability_casebased = 0.5;
                                                    } else {
                                                        probability_casebased = 0.00;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;

                    case 76:
                        if (sex == 0 && smoke == 2 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 3 && cci == 5 && anaesthetic == 0) {
                            probability_casebased = 1.00;
                        } else {
                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 10 && cci == 4 && anaesthetic == 0) {
                                probability_casebased = 1.00;
                            } else {
                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 5 && anaesthetic == 0) {
                                    probability_casebased = 0.5;
                                } else {
                                    if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 8 && cci == 5 && anaesthetic == 0) {
                                        probability_casebased = 1.00;
                                    } else {
                                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 6 && cci == 7 && anaesthetic == 0) {
                                            probability_casebased = 1.00;
                                        } else {
                                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 6 && cci == 8 && anaesthetic == 1) {
                                                probability_casebased = 1.00;
                                            } else {
                                                if (sex == 0 && smoke == 1 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 4 && cci == 6 && anaesthetic == 0) {
                                                    probability_casebased = 1.00;
                                                } else {
                                                    if (sex == 0 && smoke == 1 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 6 && cci == 4 && anaesthetic == 0) {
                                                        probability_casebased = 1.00;
                                                    } else {
                                                        if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 7 && anaesthetic == 1) {
                                                            probability_casebased = 1.00;
                                                        } else {
                                                            if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 6 && cci == 4 && anaesthetic == 1) {
                                                                probability_casebased = 1.00;
                                                            } else {
                                                                if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 4 && anaesthetic == 1) {
                                                                    probability_casebased = 1.00;
                                                                } else {
                                                                    if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 10 && cci == 9 && anaesthetic == 0) {
                                                                        probability_casebased = 1.00;
                                                                    } else {
                                                                        if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 4 && cci == 9 && anaesthetic == 0) {
                                                                            probability_casebased = 1.00;
                                                                        } else {
                                                                            probability_casebased = 0.00;
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;

                    case 77:
                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 7 && cci == 4 && anaesthetic == 0) {
                            probability_casebased = 0.5;
                        } else {
                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 4 && anaesthetic == 0) {
                                probability_casebased = 0.33;
                            } else {
                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 3 && cci == 11 && anaesthetic == 1) {
                                    probability_casebased = 1.00;
                                } else {
                                    if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 9 && cci == 6 && anaesthetic == 0) {
                                        probability_casebased = 1.00;
                                    } else {
                                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 1 && OperID == 1 && cci == 5 && anaesthetic == 1) {
                                            probability_casebased = 1.00;
                                        } else {
                                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 1 && cci == 4) {
                                                probability_casebased = 1.00;
                                            } else {
                                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 4 && cci == 7 && anaesthetic == 0) {
                                                    probability_casebased = 1.00;
                                                } else {
                                                    if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 1 && cci == 4 && anaesthetic == 0) {
                                                        probability_casebased = 1.00;
                                                    } else {
                                                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 1 && cci == 5 && anaesthetic == 1) {
                                                            probability_casebased = 1.00;
                                                        } else {
                                                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 6 && cci == 4 && anaesthetic == 0) {
                                                                probability_casebased = 1.00;
                                                            } else {
                                                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 6 && cci == 4 && anaesthetic == 1) {
                                                                    probability_casebased = 0.5;
                                                                } else {
                                                                    if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 4 && cci == 11 && anaesthetic == 0) {
                                                                        probability_casebased = 1.00;
                                                                    } else {
                                                                        if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 4 && anaesthetic == 0) {
                                                                            probability_casebased = 1.00;
                                                                        } else {
                                                                            if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 9 && cci == 4 && anaesthetic == 0) {
                                                                                probability_casebased = 1.00;
                                                                            } else {
                                                                                if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && cci == 6 && anaesthetic == 0) {
                                                                                    probability_casebased = 1.00;
                                                                                } else {
                                                                                    if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 14 && anaesthetic == 0) {
                                                                                        probability_casebased = 1.00;
                                                                                    } else {
                                                                                        if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 6 && cci == 4 && anaesthetic == 0) {
                                                                                            probability_casebased = 0.5;
                                                                                        } else {
                                                                                            if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 0 && adm == 2 && OperID == 4 && cci == 9 && anaesthetic == 0) {
                                                                                                probability_casebased = 1.00;
                                                                                            } else {
                                                                                                probability_casebased = 0.00;
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;

                    case 78:
                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 4 && anaesthetic == 0) {
                            probability_casebased = 0.5;
                        } else {
                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 4 && anaesthetic == 0) {
                                probability_casebased = 0.25;
                            } else {
                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && cci == 1) {
                                    probability_casebased = 1.00;
                                } else {
                                    if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 6 && cci == 4 && anaesthetic == 0) {
                                        probability_casebased = 0.143;
                                    } else {
                                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 6 && cci == 9 && anaesthetic == 1) {
                                            probability_casebased = 1.00;
                                        } else {
                                            if (sex == 0 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 4 && anaesthetic == 0) {
                                                probability_casebased = 1.00;
                                            } else {
                                                if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 4 && cci == 6 && anaesthetic == 0) {
                                                    probability_casebased = 1.00;
                                                } else {
                                                    if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 6 && anaesthetic == 0) {
                                                        probability_casebased = 1.00;
                                                    } else {
                                                        if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 11 && anaesthetic == 0) {
                                                            probability_casebased = 1.00;
                                                        } else {
                                                            if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 6 && cci == 5 && anaesthetic == 1) {
                                                                probability_casebased = 1.00;
                                                            } else {
                                                                probability_casebased = 0.00;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;

                    case 79:
                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 5 && anaesthetic == 0) {
                            probability_casebased = 1.00;
                        } else {
                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 6 && anaesthetic == 1) {
                                probability_casebased = 1.00;
                            } else {
                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 3 && cci == 4 && anaesthetic == 0) {
                                    probability_casebased = 1.00;
                                } else {
                                    if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 3 && cci == 5 && anaesthetic == 0) {
                                        probability_casebased = 1.00;
                                    } else {
                                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 5 && cci == 8) {
                                            probability_casebased = 1.00;
                                        } else {
                                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 8 && cci == 7 && anaesthetic == 0) {
                                                probability_casebased = 1.00;
                                            } else {
                                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 1 && OperID == 2 && cci == 5 && anaesthetic == 0) {
                                                    probability_casebased = 1.00;
                                                } else {
                                                    if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 6 && cci == 4 && anaesthetic == 0) {
                                                        probability_casebased = 0.25;
                                                    } else {
                                                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 6 && cci == 4 && anaesthetic == 1) {
                                                            probability_casebased = 0.2;
                                                        } else {
                                                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 6 && cci == 6 && anaesthetic == 1) {
                                                                probability_casebased = 1.00;
                                                            } else {
                                                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 5 && anaesthetic == 0) {
                                                                    probability_casebased = 1.00;
                                                                } else {
                                                                    if (sex == 0 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 6 && cci == 4 && anaesthetic == 0) {
                                                                        probability_casebased = 1.00;
                                                                    } else {
                                                                        if (sex == 0 && smoke == 1 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 6 && cci == 5 && anaesthetic == 1) {
                                                                            probability_casebased = 1.00;
                                                                        } else {
                                                                            if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 6 && anaesthetic == 0) {
                                                                                probability_casebased = 1.00;
                                                                            } else {
                                                                                if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 8 && anaesthetic == 0) {
                                                                                    probability_casebased = 1.00;
                                                                                } else {
                                                                                    if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 3 && cci == 7 && anaesthetic == 0) {
                                                                                        probability_casebased = 1.00;
                                                                                    } else {
                                                                                        if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 6 && cci == 6 && anaesthetic == 1) {
                                                                                            probability_casebased = 1.00;
                                                                                        } else {
                                                                                            if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 4 && anaesthetic == 0) {
                                                                                                probability_casebased = 1.00;
                                                                                            } else {
                                                                                                if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 7 && anaesthetic == 0) {
                                                                                                    probability_casebased = 1.00;
                                                                                                } else {
                                                                                                    if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 9 && cci == 6 && anaesthetic == 0) {
                                                                                                        probability_casebased = 1.00;
                                                                                                    } else {
                                                                                                        if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 1 && OperID == 2 && cci == 6) {
                                                                                                            probability_casebased = 1.00;
                                                                                                        } else {
                                                                                                            if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 1 && OperID == 1 && cci == 6 && anaesthetic == 4) {
                                                                                                                probability_casebased = 1.00;
                                                                                                            } else {
                                                                                                                if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 0 && adm == 2 && cci == 7) {
                                                                                                                    probability_casebased = 1.00;
                                                                                                                }
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;

                    case 80:
                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 4 && anaesthetic == 0) {
                            probability_casebased = 1.00;
                        } else {
                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 6 && anaesthetic == 0) {
                                probability_casebased = 1.00;
                            } else {
                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 1 && OperID == 7 && cci == 6 && anaesthetic == 0) {
                                    probability_casebased = 1.00;
                                } else {
                                    if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 6 && cci == 4 && anaesthetic == 0) {
                                        probability_casebased = 0.2;
                                    } else {
                                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 6 && cci == 4 && anaesthetic == 1) {
                                            probability_casebased = 0.25;
                                        } else {
                                            if (sex == 0 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 5 && anaesthetic == 1) {
                                                probability_casebased = 1.00;
                                            } else {
                                                if (sex == 0 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 4 && anaesthetic == 0) {
                                                    probability_casebased = 1.00;
                                                } else {
                                                    if (sex == 1 && smoke == 2 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 1 && cci == 5 && anaesthetic == 0) {
                                                        probability_casebased = 1.00;
                                                    } else {
                                                        if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 10 && anaesthetic == 0) {
                                                            probability_casebased = 1.00;
                                                        } else {
                                                            if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 1 && OperID == 5 && cci == 5 && anaesthetic == 0) {
                                                                probability_casebased = 1.00;
                                                            } else {
                                                                if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 6 && cci == 6 && anaesthetic == 0) {
                                                                    probability_casebased = 1.00;
                                                                } else {
                                                                    if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 2 && OperID == 4 && cci == 6 && anaesthetic == 0) {
                                                                        probability_casebased = 1.00;
                                                                    } else {
                                                                        if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 4 && anaesthetic == 0) {
                                                                            probability_casebased = 1.00;
                                                                        } else {
                                                                            probability_casebased = 0.00;
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;

                    case 81:
                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 5 && anaesthetic == 0) {
                            probability_casebased = 0.375;
                        } else {
                            if (sex == 0 && smoke == 2 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 5 && anaesthetic == 0) {
                                probability_casebased = 1.00;
                            } else {
                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 5 && anaesthetic == 0) {
                                    probability_casebased = 0.375;
                                } else {
                                    if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 6 && anaesthetic == 0) {
                                        probability_casebased = 1.00;
                                    } else {
                                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 7 && anaesthetic == 0) {
                                            probability_casebased = 1.00;
                                        } else {
                                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 9 && anaesthetic == 0) {
                                                probability_casebased = 1.00;
                                            } else {
                                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 1 && cci == 5 && anaesthetic == 0) {
                                                    probability_casebased = 1.00;
                                                } else {
                                                    if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && cci == 5) {
                                                        probability_casebased = 0.167;
                                                    } else {
                                                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 1 && OperID == 7 && cci == 5 && anaesthetic == 0) {
                                                            probability_casebased = 0.5;
                                                        } else {
                                                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 6 && cci == 5 && anaesthetic == 1) {
                                                                probability_casebased = 0.25;
                                                            } else {
                                                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 6 && cci == 7 && anaesthetic == 0) {
                                                                    probability_casebased = 1.00;
                                                                } else {
                                                                    if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 6 && cci == 9 && anaesthetic == 1) {
                                                                        probability_casebased = 1.00;
                                                                    } else {
                                                                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && cci == 5) {
                                                                            probability_casebased = 1.00;
                                                                        } else {
                                                                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 7 && cci == 7 && anaesthetic == 0) {
                                                                                probability_casebased = 1.00;
                                                                            } else {
                                                                                if (sex == 0 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 6 && anaesthetic == 0) {
                                                                                    probability_casebased = 1.00;
                                                                                } else {
                                                                                    if (sex == 0 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 9 && cci == 7 && anaesthetic == 0) {
                                                                                        probability_casebased = 1.00;
                                                                                    } else {
                                                                                        if (sex == 0 && smoke == 1 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 6 && cci == 7 && anaesthetic == 0) {
                                                                                            probability_casebased = 1.00;
                                                                                        } else {
                                                                                            if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 4 && cci == 5 && anaesthetic == 0) {
                                                                                                probability_casebased = 1.00;
                                                                                            } else {
                                                                                                if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 3 && cci == 9 && anaesthetic == 0) {
                                                                                                    probability_casebased = 1.00;
                                                                                                } else {
                                                                                                    if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 6 && cci == 5 && anaesthetic == 1) {
                                                                                                        probability_casebased = 1.00;
                                                                                                    } else {
                                                                                                        if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 5 && anaesthetic == 0) {
                                                                                                            probability_casebased = 1.00;
                                                                                                        } else {
                                                                                                            if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 11 && cci == 5 && anaesthetic == 0) {
                                                                                                                probability_casebased = 1.00;
                                                                                                            } else {
                                                                                                                if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 1 && OperID == 7 && cci == 7 && anaesthetic == 0) {
                                                                                                                    probability_casebased = 1.00;
                                                                                                                } else {
                                                                                                                    if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 4 && cci == 6 && anaesthetic == 0) {
                                                                                                                        probability_casebased = 1.00;
                                                                                                                    } else {
                                                                                                                        probability_casebased = 0.00;
                                                                                                                    }
                                                                                                                }
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;

                    case 82:
                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 6 && anaesthetic == 0) {
                            probability_casebased = 0.5;
                        } else {
                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 5 && anaesthetic == 1) {
                                probability_casebased = 0.5;
                            } else {
                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 6 && anaesthetic == 1) {
                                    probability_casebased = 1.00;
                                } else {
                                    if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 7 && anaesthetic == 0) {
                                        probability_casebased = 1.00;
                                    } else {
                                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 3 && cci == 9 && anaesthetic == 0) {
                                            probability_casebased = 1.00;
                                        } else {
                                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 9 && cci == 8 && anaesthetic == 0) {
                                                probability_casebased = 1.00;
                                            } else {
                                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 1 && OperID == 7 && cci == 6 && anaesthetic == 0) {
                                                    probability_casebased = 0.5;
                                                } else {
                                                    if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 9 && cci == 5 && anaesthetic == 0) {
                                                        probability_casebased = 0.5;
                                                    } else {
                                                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 1 && cci == 7 && anaesthetic == 0) {
                                                            probability_casebased = 1.00;
                                                        } else {
                                                            if (sex == 0 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 5 && anaesthetic == 0) {
                                                                probability_casebased = 0.25;
                                                            } else {
                                                                if (sex == 0 && smoke == 1 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 5 && cci == 8 && anaesthetic == 0) {
                                                                    probability_casebased = 1.00;
                                                                } else {
                                                                    if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 5 && anaesthetic == 0) {
                                                                        probability_casebased = 0.5;
                                                                    } else {
                                                                        if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 6 && cci == 5 && anaesthetic == 0) {
                                                                            probability_casebased = 0.5;
                                                                        } else {
                                                                            if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 6 && cci == 5 && anaesthetic == 1) {
                                                                                probability_casebased = 1.00;
                                                                            } else {
                                                                                if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 5 && anaesthetic == 0) {
                                                                                    probability_casebased = 1.00;
                                                                                } else {
                                                                                    if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 6 && anaesthetic == 0) {
                                                                                        probability_casebased = 1.00;
                                                                                    } else {
                                                                                        if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 9 && cci == 5 && anaesthetic == 0) {
                                                                                            probability_casebased = 1.00;
                                                                                        } else {
                                                                                            if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && cci == 10) {
                                                                                                probability_casebased = 1.00;
                                                                                            } else {
                                                                                                if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 4 && cci == 5 && anaesthetic == 0) {
                                                                                                    probability_casebased = 1.00;
                                                                                                } else {
                                                                                                    if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 8 && cci == 5 && anaesthetic == 0) {
                                                                                                        probability_casebased = 1.00;
                                                                                                    } else {
                                                                                                        probability_casebased = 0.00;
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;

                    case 83:
                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 5 && anaesthetic == 1) {
                            probability_casebased = 1.00;
                        } else {
                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 5 && anaesthetic == 0) {
                                probability_casebased = 0.5;
                            } else {
                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 5 && anaesthetic == 0) {
                                    probability_casebased = 0.167;
                                } else {
                                    if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 7 && anaesthetic == 0) {
                                        probability_casebased = 0.5;
                                    } else {
                                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 10 && anaesthetic == 0) {
                                            probability_casebased = 1.00;
                                        } else {
                                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 1 && OperID == 7 && cci == 5 && anaesthetic == 0) {
                                                probability_casebased = 1.00;
                                            } else {
                                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 1 && OperID == 2 && cci == 5 && anaesthetic == 1) {
                                                    probability_casebased = 1.00;
                                                } else {
                                                    if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 1 && OperID == 2 && cci == 6 && anaesthetic == 0) {
                                                        probability_casebased = 1.00;
                                                    } else {
                                                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && cci == 5) {
                                                            probability_casebased = 1.00;
                                                        } else {
                                                            if (sex == 0 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 6 && cci == 5 && anaesthetic == 1) {
                                                                probability_casebased = 1.00;
                                                            } else {
                                                                if (sex == 1 && smoke == 2 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 8 && anaesthetic == 0) {
                                                                    probability_casebased = 1.00;
                                                                } else {
                                                                    if (sex == 1 && smoke == 0 && alcohol == 1 && emnone == 1 && adm == 0 && cci == 6) {
                                                                        probability_casebased = 1.00;
                                                                    } else {
                                                                        if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 5 && anaesthetic == 1) {
                                                                            probability_casebased = 1.00;
                                                                        } else {
                                                                            if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 6 && anaesthetic == 0) {
                                                                                probability_casebased = 1.00;
                                                                            } else {
                                                                                if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 5 && anaesthetic == 0) {
                                                                                    probability_casebased = 1.00;
                                                                                } else {
                                                                                    if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 4 && cci == 5 && anaesthetic == 0) {
                                                                                        probability_casebased = 1.00;
                                                                                    } else {
                                                                                        probability_casebased = 0.00;
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;

                    case 84:
                        if (sex == 0 && smoke == 2 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 7 && anaesthetic == 0) {
                            probability_casebased = 1.00;
                        } else {
                            if (sex == 0 && smoke == 2 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 6 && anaesthetic == 0) {
                                probability_casebased = 0.5;
                            } else {
                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 5 && anaesthetic == 0) {
                                    probability_casebased = 0.25;
                                } else {
                                    if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 5 && anaesthetic == 0) {
                                        probability_casebased = 0.25;
                                    } else {
                                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 5 && anaesthetic == 1) {
                                            probability_casebased = 0.5;
                                        } else {
                                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 7 && anaesthetic == 0) {
                                                probability_casebased = 0.333;
                                            } else {
                                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 3 && cci == 5 && anaesthetic == 0) {
                                                    probability_casebased = 0.5;
                                                } else {
                                                    if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 3 && cci == 6 && anaesthetic == 0) {
                                                        probability_casebased = 1.00;
                                                    } else {
                                                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 3 && cci == 8 && anaesthetic == 1) {
                                                            probability_casebased = 1.00;
                                                        } else {
                                                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 5 && cci == 5 && anaesthetic == 0) {
                                                                probability_casebased = 0.333;
                                                            } else {
                                                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && cci == 5) {
                                                                    probability_casebased = 0.5;
                                                                } else {
                                                                    if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && cci == 6) {
                                                                        probability_casebased = 0.5;
                                                                    } else {
                                                                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && cci == 8) {
                                                                            probability_casebased = 1.00;
                                                                        } else {
                                                                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 1 && OperID == 2 && cci == 8 && anaesthetic == 1) {
                                                                                probability_casebased = 1.00;
                                                                            } else {
                                                                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 9 && cci == 8 && anaesthetic == 0) {
                                                                                    probability_casebased = 1.00;
                                                                                } else {
                                                                                    if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 1 && cci == 5 && anaesthetic == 1) {
                                                                                        probability_casebased = 1.00;
                                                                                    } else {
                                                                                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && cci == 6) {
                                                                                            probability_casebased = 1.00;
                                                                                        } else {
                                                                                            if (sex == 0 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 5 && anaesthetic == 0) {
                                                                                                probability_casebased = 0.5;
                                                                                            } else {
                                                                                                if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 5 && anaesthetic == 0) {
                                                                                                    probability_casebased = 0.5;
                                                                                                } else {
                                                                                                    if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 4 && cci == 8 && anaesthetic == 0) {
                                                                                                        probability_casebased = 1.00;
                                                                                                    } else {
                                                                                                        if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 6 && cci == 8 && anaesthetic == 1) {
                                                                                                            probability_casebased = 1.00;
                                                                                                        } else {
                                                                                                            probability_casebased = 0.00;
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;

                    case 85:
                        if (sex == 0 && smoke == 2 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 7 && anaesthetic == 0) {
                            probability_casebased = 0.5;
                        } else {
                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 5 && anaesthetic == 0) {
                                probability_casebased = 0.375;
                            } else {
                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 5 && anaesthetic == 1) {
                                    probability_casebased = 1.00;
                                } else {
                                    if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 6 && anaesthetic == 1) {
                                        probability_casebased = 1.00;
                                    } else {
                                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 7 && anaesthetic == 1) {
                                            probability_casebased = 1.00;
                                        } else {
                                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 9 && anaesthetic == 0) {
                                                probability_casebased = 1.00;
                                            } else {
                                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 1 && cci == 9 && anaesthetic == 0) {
                                                    probability_casebased = 1.00;
                                                } else {
                                                    if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 1 && OperID == 2 && cci == 6 && anaesthetic == 0) {
                                                        probability_casebased = 0.5;
                                                    } else {
                                                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 3 && cci == 5) {
                                                            probability_casebased = 1.00;
                                                        } else {
                                                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 6 && cci == 5 && anaesthetic == 0) {
                                                                probability_casebased = 0.5;
                                                            } else {
                                                                if (sex == 0 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 6 && anaesthetic == 0) {
                                                                    probability_casebased = 1.00;
                                                                } else {
                                                                    if (sex == 0 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 6 && anaesthetic == 0) {
                                                                        probability_casebased = 1.00;
                                                                    } else {
                                                                        if (sex == 0 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 5 && cci == 6 && anaesthetic == 0) {
                                                                            probability_casebased = 1.00;
                                                                        } else {
                                                                            if (sex == 0 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 6 && cci == 5 && anaesthetic == 1) {
                                                                                probability_casebased = 0.5;
                                                                            } else {
                                                                                if (sex == 1 && smoke == 2 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 10 && cci == 5 && anaesthetic == 0) {
                                                                                    probability_casebased = 1.00;
                                                                                } else {
                                                                                    if (sex == 1 && smoke == 2 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 8 && cci == 5 && anaesthetic == 0) {
                                                                                        probability_casebased = 1.00;
                                                                                    } else {
                                                                                        if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 5 && anaesthetic == 0) {
                                                                                            probability_casebased = 0.5;
                                                                                        } else {
                                                                                            if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 4 && cci == 5 && anaesthetic == 0) {
                                                                                                probability_casebased = 0.5;
                                                                                            } else {
                                                                                                if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 2 && OperID == 4 && cci == 6 && anaesthetic == 0) {
                                                                                                    probability_casebased = 1.00;
                                                                                                } else {
                                                                                                    if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 2 && cci == 5) {
                                                                                                        probability_casebased = 1.00;
                                                                                                    } else {
                                                                                                        if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 8 && cci == 5 && anaesthetic == 0) {
                                                                                                            probability_casebased = 1.00;
                                                                                                        } else {
                                                                                                            probability_casebased = 0.00;
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;

                    case 86:
                        if (sex == 0 && smoke == 2 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 6 && anaesthetic == 0) {
                            probability_casebased = 1.00;
                        } else {
                            if (sex == 0 && smoke == 2 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 5 && anaesthetic == 0) {
                                probability_casebased = 1.00;
                            } else {
                                if (sex == 0 && smoke == 2 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 9 && cci == 5 && anaesthetic == 0) {
                                    probability_casebased = 1.00;
                                } else {
                                    if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 5 && anaesthetic == 0) {
                                        probability_casebased = 1.00;
                                    } else {
                                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 5 && anaesthetic == 1) {
                                            probability_casebased = 0.333;
                                        } else {
                                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 7 && anaesthetic == 0) {
                                                probability_casebased = 0.5;
                                            } else {
                                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 8 && anaesthetic == 1) {
                                                    probability_casebased = 1.00;
                                                } else {
                                                    if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 7 && anaesthetic == 0) {
                                                        probability_casebased = 1.00;
                                                    } else {
                                                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 3 && cci == 5 && anaesthetic == 0) {
                                                            probability_casebased = 1.00;
                                                        } else {
                                                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 3 && cci == 5 && anaesthetic == 1) {
                                                                probability_casebased = 1.00;
                                                            } else {
                                                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 1 && OperID == 7 && cci == 5 && anaesthetic == 1) {
                                                                    probability_casebased = 0.5;
                                                                } else {
                                                                    if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 1 && cci == 9 && anaesthetic == 0) {
                                                                        probability_casebased = 1.00;
                                                                    } else {
                                                                        if (sex == 0 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 7 && anaesthetic == 0) {
                                                                            probability_casebased = 1.00;
                                                                        } else {
                                                                            if (sex == 0 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 9 && anaesthetic == 0) {
                                                                                probability_casebased = 1.00;
                                                                            } else {
                                                                                if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 7 && anaesthetic == 0) {
                                                                                    probability_casebased = 1.00;
                                                                                } else {
                                                                                    if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 3 && cci == 6 && anaesthetic == 0) {
                                                                                        probability_casebased = 1.00;
                                                                                    } else {
                                                                                        probability_casebased = 0.00;
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;

                    case 87:
                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 5 && anaesthetic == 0) {
                            probability_casebased = 0.5;
                        } else {
                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 6 && anaesthetic == 0) {
                                probability_casebased = 0.2;
                            } else {
                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 7 && anaesthetic == 0) {
                                    probability_casebased = 0.5;
                                } else {
                                    if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 5 && anaesthetic == 0) {
                                        probability_casebased = 0.286;
                                    } else {
                                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 5 && anaesthetic == 1) {
                                            probability_casebased = 0.2;
                                        } else {
                                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 6 && anaesthetic == 2) {
                                                probability_casebased = 1.00;
                                            } else {
                                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 9 && anaesthetic == 0) {
                                                    probability_casebased = 1.00;
                                                } else {
                                                    if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 3 && cci == 9 && anaesthetic == 1) {
                                                        probability_casebased = 1.00;
                                                    } else {
                                                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 1 && cci == 8 && anaesthetic == 0) {
                                                            probability_casebased = 1.00;
                                                        } else {
                                                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 1 && OperID == 2 && cci == 9 && anaesthetic == 0) {
                                                                probability_casebased = 1.00;
                                                            } else {
                                                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 1 && OperID == 3 && cci == 5 && anaesthetic == 0) {
                                                                    probability_casebased = 1.00;
                                                                } else {
                                                                    if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 3 && cci == 6 && anaesthetic == 0) {
                                                                        probability_casebased = 1.00;
                                                                    } else {
                                                                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 4 && cci == 5 && anaesthetic == 0) {
                                                                            probability_casebased = 1.00;
                                                                        } else {
                                                                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 1 && cci == 5 && anaesthetic == 0) {
                                                                                probability_casebased = 0.5;
                                                                            } else {
                                                                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 2 && cci == 5) {
                                                                                    probability_casebased = 0.5;
                                                                                } else {
                                                                                    if (sex == 0 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 5 && anaesthetic == 0) {
                                                                                        probability_casebased = 1.00;
                                                                                    } else {
                                                                                        if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 6 && anaesthetic == 0) {
                                                                                            probability_casebased = 1.00;
                                                                                        } else {
                                                                                            if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 9 && anaesthetic == 0) {
                                                                                                probability_casebased = 1.00;
                                                                                            } else {
                                                                                                if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 3 && cci == 6 && anaesthetic == 0) {
                                                                                                    probability_casebased = 1.00;
                                                                                                } else {
                                                                                                    if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 0 && adm == 0 && OperID == 6 && cci == 5 && anaesthetic == 1) {
                                                                                                        probability_casebased = 1.00;
                                                                                                    } else {
                                                                                                        if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 7 && anaesthetic == 1) {
                                                                                                            probability_casebased = 1.00;
                                                                                                        } else {
                                                                                                            if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 8 && anaesthetic == 0) {
                                                                                                                probability_casebased = 1.00;
                                                                                                            } else {
                                                                                                                if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 1 && cci == 7 && anaesthetic == 1) {
                                                                                                                    probability_casebased = 1.00;
                                                                                                                } else {
                                                                                                                    probability_casebased = 0.00;
                                                                                                                }
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;

                    case 88:
                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 5 && anaesthetic == 0) {
                            probability_casebased = 0.4;
                        } else {
                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 5 && anaesthetic == 1) {
                                probability_casebased = 1.00;
                            } else {
                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 5 && anaesthetic == 0) {
                                    probability_casebased = 0.333;
                                } else {
                                    if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 5 && anaesthetic == 1) {
                                        probability_casebased = 0.5;
                                    } else {
                                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 6 && anaesthetic == 0) {
                                            probability_casebased = 0.5;
                                        } else {
                                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 7 && anaesthetic == 0) {
                                                probability_casebased = 0.5;
                                            } else {
                                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 5 && anaesthetic == 0) {
                                                    probability_casebased = 1.00;
                                                } else {
                                                    if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 1 && OperID == 2 && cci == 5 && anaesthetic == 0) {
                                                        probability_casebased = 0.25;
                                                    } else {
                                                        if (sex == 1 && smoke == 2 && alcohol == 0 && emnone == 1 && adm == 1 && OperID == 2 && cci == 7 && anaesthetic == 0) {
                                                            probability_casebased = 1.00;
                                                        } else {
                                                            if (sex == 1 && smoke == 0 && alcohol == 2 && emnone == 1 && adm == 0 && cci == 5) {
                                                                probability_casebased = 1.00;
                                                            } else {
                                                                if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 1 && OperID == 8 && cci == 8 && anaesthetic == 0) {
                                                                    probability_casebased = 1.00;
                                                                } else {
                                                                    if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 1 && cci == 5 && anaesthetic == 0) {
                                                                        probability_casebased = 1.00;
                                                                    } else {
                                                                        probability_casebased = 0.00;
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;

                    case 89:
                        if (sex == 0 && smoke == 2 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 6 && anaesthetic == 0) {
                            probability_casebased = 1.00;
                        } else {
                            if (sex == 0 && smoke == 2 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 8 && anaesthetic == 0) {
                                probability_casebased = 1.00;
                            } else {
                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 5 && anaesthetic == 0) {
                                    probability_casebased = 0.167;
                                } else {
                                    if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 6 && anaesthetic == 0) {
                                        probability_casebased = 0.5;
                                    } else {
                                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 5 && anaesthetic == 0) {
                                            probability_casebased = 0.444;
                                        } else {
                                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 7 && anaesthetic == 0) {
                                                probability_casebased = 1.00;
                                            } else {
                                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 11 && anaesthetic == 1) {
                                                    probability_casebased = 1.00;
                                                } else {
                                                    if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && cci == 5) {
                                                        probability_casebased = 0.2;
                                                    } else {
                                                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && cci == 6) {
                                                            probability_casebased = 0.333;
                                                        } else {
                                                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 6 && anaesthetic == 1) {
                                                                probability_casebased = 1.00;
                                                            } else {
                                                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 6 && cci == 5 && anaesthetic == 0) {
                                                                    probability_casebased = 0.5;
                                                                } else {
                                                                    if (sex == 0 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 1 && cci == 5 && anaesthetic == 1) {
                                                                        probability_casebased = 1.00;
                                                                    } else {
                                                                        if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 5 && anaesthetic == 0) {
                                                                            probability_casebased = 0.4;
                                                                        } else {
                                                                            if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 5 && anaesthetic == 1) {
                                                                                probability_casebased = 1.00;
                                                                            } else {
                                                                                if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 1 && cci == 7 && anaesthetic == 0) {
                                                                                    probability_casebased = 1.00;
                                                                                } else {
                                                                                    if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && cci == 5) {
                                                                                        probability_casebased = 1.00;
                                                                                    } else {
                                                                                        if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 1 && OperID == 7 && cci == 5 && anaesthetic == 0) {
                                                                                            probability_casebased = 1.00;
                                                                                        } else {
                                                                                            probability_casebased = 0.00;
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;

                    case 90:
                        if (sex == 0 && smoke == 2 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 6 && anaesthetic == 0) {
                            probability_casebased = 1.00;
                        } else {
                            if (sex == 0 && smoke == 2 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 1 && cci == 9 && anaesthetic == 0) {
                                probability_casebased = 1.00;
                            } else {
                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 5 && anaesthetic == 0) {
                                    probability_casebased = 1.00;
                                } else {
                                    if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 6 && anaesthetic == 0) {
                                        probability_casebased = 1.00;
                                    } else {
                                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 7 && anaesthetic == 0) {
                                            probability_casebased = 1.00;
                                        } else {
                                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 5 && anaesthetic == 0) {
                                                probability_casebased = 0.167;
                                            } else {
                                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 6 && anaesthetic == 0) {
                                                    probability_casebased = 0.333;
                                                } else {
                                                    if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 7 && anaesthetic == 0) {
                                                        probability_casebased = 0.5;
                                                    } else {
                                                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 3 && cci == 5 && anaesthetic == 0) {
                                                            probability_casebased = 0.5;
                                                        } else {
                                                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 1 && OperID == 2 && cci == 6 && anaesthetic == 0) {
                                                                probability_casebased = 1.00;
                                                            } else {
                                                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && cci == 6 && anaesthetic == 0) {
                                                                    probability_casebased = 1.00;
                                                                } else {
                                                                    if (sex == 0 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && cci == 6) {
                                                                        probability_casebased = 1.00;
                                                                    } else {
                                                                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 4 && cci == 5 && anaesthetic == 0) {
                                                                            probability_casebased = 1.00;
                                                                        } else {
                                                                            if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 11 && anaesthetic == 1) {
                                                                                probability_casebased = 1.00;
                                                                            } else {
                                                                                if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 5 && anaesthetic == 0) {
                                                                                    probability_casebased = 1.00;
                                                                                } else {
                                                                                    probability_casebased = 0.00;
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;

                    case 91:
                        if (sex == 0 && smoke == 2 && alcohol == 0 && emnone == 1 && cci == 7) {
                            probability_casebased = 1.00;
                        } else {
                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 6 && anaesthetic == 0) {
                                probability_casebased = 1.00;
                            } else {
                                if (sex == 0 && smoke == 2 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 6 && anaesthetic == 1) {
                                    probability_casebased = 0.5;
                                } else {
                                    if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 8 && anaesthetic == 1) {
                                        probability_casebased = 1.00;
                                    } else {
                                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 9 && anaesthetic == 0) {
                                            probability_casebased = 1.00;
                                        } else {
                                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 6 && anaesthetic == 0) {
                                                probability_casebased = 0.286;
                                            } else {
                                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 6 && anaesthetic == 1) {
                                                    probability_casebased = 1.00;
                                                } else {
                                                    if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 8 && anaesthetic == 0) {
                                                        probability_casebased = 1.00;
                                                    } else {
                                                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 9 && anaesthetic == 0) {
                                                            probability_casebased = 0.5;
                                                        } else {
                                                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 11 && anaesthetic == 0) {
                                                                probability_casebased = 1.00;
                                                            } else {
                                                                if (sex == 0 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 8 && anaesthetic == 0) {
                                                                    probability_casebased = 0.5;
                                                                } else {
                                                                    if (sex == 0 && smoke == 2 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 8 && anaesthetic == 0) {
                                                                        probability_casebased = 1.00;
                                                                    } else {
                                                                        if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 4 && cci == 9 && anaesthetic == 0) {
                                                                            probability_casebased = 1.00;
                                                                        } else {
                                                                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 6 && anaesthetic == 0) {
                                                                                probability_casebased = 1.00;
                                                                            } else {
                                                                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 7 && anaesthetic == 0) {
                                                                                    probability_casebased = 1.00;
                                                                                } else {
                                                                                    if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 5 && cci == 10) {
                                                                                        probability_casebased = 1.00;
                                                                                    } else {
                                                                                        if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 1 && cci == 6 && anaesthetic == 0) {
                                                                                            probability_casebased = 1.00;
                                                                                        } else {
                                                                                            if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && cci == 6) {
                                                                                                probability_casebased = 1.00;
                                                                                            } else {
                                                                                                if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 6 && anaesthetic == 1) {
                                                                                                    probability_casebased = 1.00;
                                                                                                } else {
                                                                                                    if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 6 && anaesthetic == 0) {
                                                                                                        probability_casebased = 0.6;
                                                                                                    } else {
                                                                                                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 6 && anaesthetic == 1) {
                                                                                                            probability_casebased = 0.333;
                                                                                                        } else {
                                                                                                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 7 && anaesthetic == 0) {
                                                                                                                probability_casebased = 1.00;
                                                                                                            } else {
                                                                                                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 7 && anaesthetic == 0) {
                                                                                                                    probability_casebased = 0.5;
                                                                                                                } else {
                                                                                                                    if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 1 && OperID == 2 && cci == 7 && anaesthetic == 0) {
                                                                                                                        probability_casebased = 1.00;
                                                                                                                    } else {
                                                                                                                        if (sex == 0 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 1 && OperID == 7 && cci == 11 && anaesthetic == 1) {
                                                                                                                            probability_casebased = 1.00;
                                                                                                                        } else {
                                                                                                                            if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 7 && anaesthetic == 1) {
                                                                                                                                probability_casebased = 1.00;
                                                                                                                            } else {
                                                                                                                                if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 11 && anaesthetic == 0) {
                                                                                                                                    probability_casebased = 1.00;
                                                                                                                                } else {
                                                                                                                                    if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 7) {
                                                                                                                                        probability_casebased = 1.00;
                                                                                                                                    } else {
                                                                                                                                        if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 1 && OperID == 1 && cci == 11 && anaesthetic == 1) {
                                                                                                                                            probability_casebased = 1.00;
                                                                                                                                        }
                                                                                                                                    }
                                                                                                                                }
                                                                                                                            }
                                                                                                                        }

                                                                                                                    }
                                                                                                                }
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;

                    case 93:
                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 7 && anaesthetic == 3) {
                            probability_casebased = 1.00;
                        } else {
                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 9 && anaesthetic == 0) {
                                probability_casebased = 1.00;
                            } else {
                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 9 && anaesthetic == 1) {
                                    probability_casebased = 1.00;
                                } else {
                                    if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 10 && anaesthetic == 0) {
                                        probability_casebased = 1.00;
                                    } else {
                                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 6 && anaesthetic == 2) {
                                            probability_casebased = 1.00;
                                        } else {
                                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 8 && anaesthetic == 0) {
                                                probability_casebased = 0.5;
                                            } else {
                                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 10 && anaesthetic == 1) {
                                                    probability_casebased = 1.00;
                                                } else {
                                                    if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && cci == 6) {
                                                        probability_casebased = 0.667;
                                                    } else {
                                                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && cci == 11) {
                                                            probability_casebased = 1.00;
                                                        } else {
                                                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 1 && OperID == 3 && cci == 6 && anaesthetic == 0) {
                                                                probability_casebased = 1.00;
                                                            } else {
                                                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 1 && OperID == 3 && cci == 7 && anaesthetic == 0) {
                                                                    probability_casebased = 1.00;
                                                                } else {
                                                                    if (sex == 0 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 6 && anaesthetic == 0) {
                                                                        probability_casebased = 1.00;
                                                                    } else {
                                                                        if (sex == 0 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 1 && OperID == 7 && cci == 6 && anaesthetic == 0) {
                                                                            probability_casebased = 1.00;
                                                                        } else {
                                                                            if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 12 && anaesthetic == 0) {
                                                                                probability_casebased = 1.00;
                                                                            } else {
                                                                                probability_casebased = 0.00;
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;

                    case 94:
                        if (sex == 0 && smoke == 2 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 8 && anaesthetic == 1) {
                            probability_casebased = 1.00;
                        } else {
                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 6 && anaesthetic == 1) {
                                probability_casebased = 1.00;
                            } else {
                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 7 && anaesthetic == 0) {
                                    probability_casebased = 1.00;
                                } else {
                                    if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 8 && anaesthetic == 0) {
                                        probability_casebased = 1.00;
                                    } else {
                                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 10 && anaesthetic == 0) {
                                            probability_casebased = 1.00;
                                        } else {
                                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 3 && cci == 6 && anaesthetic == 1) {
                                                probability_casebased = 1.00;
                                            } else {
                                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 9 && cci == 7 && anaesthetic == 3) {
                                                    probability_casebased = 1.00;
                                                } else {
                                                    if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && cci == 7) {
                                                        probability_casebased = 1.00;
                                                    } else {
                                                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 1 && OperID == 2 && cci == 8 && anaesthetic == 0) {
                                                            probability_casebased = 1.00;
                                                        } else {
                                                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 1 && OperID == 3 && cci == 6 && anaesthetic == 0) {
                                                                probability_casebased = 1.00;
                                                            } else {
                                                                if (sex == 0 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 9 && cci == 6 && anaesthetic == 2) {
                                                                    probability_casebased = 1.00;
                                                                } else {
                                                                    if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 6 && anaesthetic == 1) {
                                                                        probability_casebased = 1.00;
                                                                    } else {
                                                                        if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 9 && anaesthetic == 1) {
                                                                            probability_casebased = 1.00;
                                                                        } else {
                                                                            if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 6 && anaesthetic == 0) {
                                                                                probability_casebased = 1.00;
                                                                            } else {
                                                                                if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 1 && OperID == 7 && cci == 6 && anaesthetic == 0) {
                                                                                    probability_casebased = 1.00;
                                                                                } else {
                                                                                    probability_casebased = 0.00;
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;

                    case 95:
                        if (sex == 0 && smoke == 0 && alcohol == 2 && emnone == 1 && adm == 0 && OperID == 2 && cci == 6 && anaesthetic == 0) {
                            probability_casebased = 1.00;
                        } else {
                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 7 && anaesthetic == 0) {
                                probability_casebased = 1.00;
                            } else {
                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 11 && anaesthetic == 0) {
                                    probability_casebased = 1.00;
                                } else {
                                    if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 7 && anaesthetic == 1) {
                                        probability_casebased = 1.00;
                                    } else {
                                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 1 && OperID == 2 && cci == 10 && anaesthetic == 0) {
                                            probability_casebased = 1.00;
                                        } else {
                                            if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 6 && anaesthetic == 0) {
                                                probability_casebased = 1.00;
                                            } else {
                                                if (sex == 1 && smoke == 1 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 6 && anaesthetic == 1) {
                                                    probability_casebased = 1.00;
                                                } else {
                                                    probability_casebased = 0.00;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;

                    case 96:
                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 6 && anaesthetic == 0) {
                            probability_casebased = 1.00;
                        } else {
                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 7 && anaesthetic == 1) {
                                probability_casebased = 1.00;
                            } else {
                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 6 && anaesthetic == 0) {
                                    probability_casebased = 1.00;
                                } else {
                                    probability_casebased = 0.00;
                                }
                            }
                        }
                        break;

                    case 97:
                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 7 && cci == 6 && anaesthetic == 1) {
                            probability_casebased = 1.00;
                        } else {
                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 7 && anaesthetic == 0) {
                                probability_casebased = 1.00;
                            } else {
                                if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 5 && cci == 8 && anaesthetic == 1) {
                                    probability_casebased = 1.00;
                                } else {
                                    probability_casebased = 0.00;
                                }
                            }
                        }
                        break;

                    case 98:
                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && cci == 8) {
                            probability_casebased = 1.00;
                        } else {
                            probability_casebased = 0.00;
                        }
                        break;

                    case 99:
                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 6 && anaesthetic == 0) {
                            probability_casebased = 1.00;
                        } else {
                            if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 6 && anaesthetic == 1) {
                                probability_casebased = 1.00;
                            } else {
                                if (sex == 1 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 6 && anaesthetic == 0) {
                                    probability_casebased = 1.00;
                                }
                            }
                        }
                        break;

                    case 100:
                        if (sex == 0 && smoke == 0 && alcohol == 0 && emnone == 1 && adm == 0 && OperID == 2 && cci == 7 && anaesthetic == 0) {
                            probability_casebased = 1.00;
                        } else {
                            probability_casebased = 0.00;
                        }
                        break;
                    default:
                        probability_casebased = 0.00;
                        break;
                }

                // Rule - based model
                if (probability_CART >= 0.73 || probability_NB >= 0.731) {
                    probability_suggested = 1;
                    rulebased_output = "Metcall";
                } else {
                    probability_suggested = 0;
                    rulebased_output = "NO Metcall";
                }

                if (probability_CART>=probability_NB){
                    probability_final = probability_CART;
                } else {
                    probability_final = probability_NB;
                }


                //View1.setText("Probability:" + probability_final);
                View1.setText("CART Model: " + probability_CART);
                View2.setText("Naive Bayes Model: " + probability_NB);
                View3.setText("" + probability_casebased);
                View4.setText("" + rulebased_output);
                View5.setText("Probability:" + probability_final);

                button_result = (Button) findViewById(R.id.b_result_home);
                button_result.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(result.this, MainActivity.class);
                        startActivity(intent);
                    }
                });

                button_result = (Button) findViewById(R.id.b_result_back);
                button_result.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(result.this, triage.class);
                        startActivity(intent);
                    }
                });
            }
        }
    }
}

