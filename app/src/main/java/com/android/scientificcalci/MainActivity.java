package com.android.scientificcalci;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    private TextView tvMain, tvSec;
    private boolean isEvaluated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvMain = findViewById(R.id.tvmain);
        tvSec = findViewById(R.id.tvsec);

        // Standard Numbers
        int[] numericButtonIds = {
                R.id.b0, R.id.b1, R.id.b2, R.id.b3, R.id.b4,
                R.id.b5, R.id.b6, R.id.b7, R.id.b8, R.id.b9, R.id.bdot
        };

        for (int id : numericButtonIds) {
            findViewById(id).setOnClickListener(v -> {
                MaterialButton b = (MaterialButton) v;
                if (isEvaluated) {
                    tvMain.setText("");
                    isEvaluated = false;
                }
                tvMain.setText(tvMain.getText().toString() + b.getText().toString());
            });
        }

        // Basic Operators
        findViewById(R.id.bplus).setOnClickListener(v -> appendSymbol("+"));
        findViewById(R.id.bmin).setOnClickListener(v -> appendSymbol("-"));
        findViewById(R.id.bmul).setOnClickListener(v -> appendSymbol("×"));
        findViewById(R.id.bdiv).setOnClickListener(v -> appendSymbol("÷"));
        findViewById(R.id.bb1).setOnClickListener(v -> appendSymbol("("));
        findViewById(R.id.bb2).setOnClickListener(v -> appendSymbol(")"));

        // Scientific Functions & Constants
        findViewById(R.id.bsin).setOnClickListener(v -> appendSymbol("sin("));
        findViewById(R.id.bcos).setOnClickListener(v -> appendSymbol("cos("));
        findViewById(R.id.btan).setOnClickListener(v -> appendSymbol("tan("));
        findViewById(R.id.blog).setOnClickListener(v -> appendSymbol("log("));
        findViewById(R.id.bln).setOnClickListener(v -> appendSymbol("ln("));
        findViewById(R.id.bsqrt).setOnClickListener(v -> appendSymbol("√("));
        findViewById(R.id.bpi).setOnClickListener(v -> appendSymbol("π"));
        findViewById(R.id.be).setOnClickListener(v -> appendSymbol("e"));

        // Single-Operand Instant Operations
        findViewById(R.id.bsquare).setOnClickListener(v -> calculateInstant("^2"));
        findViewById(R.id.bfact).setOnClickListener(v -> calculateInstant("!"));
        findViewById(R.id.binv).setOnClickListener(v -> calculateInstant("^(-1)"));

        // Clear and Reset
        findViewById(R.id.bac).setOnClickListener(v -> {
            tvMain.setText("");
            tvSec.setText("");
            isEvaluated = false;
        });

        findViewById(R.id.bc).setOnClickListener(v -> {
            String str = tvMain.getText().toString();
            if (!str.isEmpty()) {
                tvMain.setText(str.substring(0, str.length() - 1));
            }
        });

        // Equals Operation
        findViewById(R.id.bequal).setOnClickListener(v -> evaluateExpression());
    }

    private void appendSymbol(String symbol) {
        if (isEvaluated) {
            isEvaluated = false;
        }
        tvMain.setText(tvMain.getText().toString() + symbol);
    }

    private void calculateInstant(String operation) {
        String currentText = tvMain.getText().toString();
        if (!currentText.isEmpty()) {
            tvSec.setText(currentText + operation);
            try {
                double val = Double.parseDouble(currentText);
                double result = 0;

                if (operation.equals("^2")) {
                    result = val * val;
                } else if (operation.equals("!")) {
                    result = factorial((int) val);
                } else if (operation.equals("^(-1)")) {
                    result = 1 / val;
                }

                String resStr = formatResult(result);
                tvMain.setText(resStr);
                isEvaluated = true;
            } catch (Exception e) {
                tvMain.setText("Error");
            }
        }
    }

    private double factorial(int n) {
        if (n < 0) return Double.NaN;
        if (n == 0 || n == 1) return 1;
        double fact = 1;
        for (int i = 2; i <= n; i++) {
            fact *= i;
        }
        return fact;
    }

    private void evaluateExpression() {
        String exp = tvMain.getText().toString();
        if (exp.isEmpty()) return;

        tvSec.setText(exp);
        try {
            double result = eval(exp);
            String resStr = formatResult(result);
            tvMain.setText(resStr);
            isEvaluated = true;
        } catch (Exception e) {
            tvMain.setText("Error");
        }
    }

    private String formatResult(double result) {
        if (Double.isNaN(result) || Double.isInfinite(result)) {
            return "Error";
        }
        if (result == (long) result) {
            return String.format("%d", (long) result);
        }
        return String.valueOf(result);
    }

    // Expression Evaluator (Parses basic arithmetic, trig, logs, e, and constants)
    private double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char) ch);
                return x;
            }

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if (eat('+')) x += parseTerm();
                    else if (eat('-')) x -= parseTerm();
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if (eat('×') || eat('*')) x *= parseFactor();
                    else if (eat('÷') || eat('/')) x /= parseFactor();
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return +parseFactor();
                if (eat('-')) return -parseFactor();

                double x;
                int startPos = this.pos;

                if (eat('(')) {
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') {
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (eat('π')) {
                    x = Math.PI;
                } else if (eat('e')) {
                    x = Math.E;
                } else if (ch >= 'a' && ch <= 'z') {
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    switch (func) {
                        case "sin":
                            x = Math.sin(Math.toRadians(x));
                            break;
                        case "cos":
                            x = Math.cos(Math.toRadians(x));
                            break;
                        case "tan":
                            x = Math.tan(Math.toRadians(x));
                            break;
                        case "log":
                            x = Math.log10(x);
                            break;
                        case "ln":
                            x = Math.log(x);
                            break;
                        case "√":
                        case "sqrt":
                            x = Math.sqrt(x);
                            break;
                        default:
                            throw new RuntimeException("Unknown function: " + func);
                    }
                } else {
                    throw new RuntimeException("Unexpected char: " + (char) ch);
                }

                return x;
            }
        }.parse();
    }
}