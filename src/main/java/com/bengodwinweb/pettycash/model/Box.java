package com.bengodwinweb.pettycash.model;

import com.bengodwinweb.pettycash.util.Currency;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Document(collection = "box")
public class Box {

    @Id
    private String id;

    @NotNull
    @Min(0)
    private int twenties = 0;

    @NotNull
    @Min(0)
    private int tens = 0;

    @NotNull
    @Min(0)
    private int fives = 0;

    @NotNull
    @Min(0)
    private int ones = 0;

    @NotNull
    @Min(0)
    private int qrolls = 0;

    @NotNull
    @Min(0)
    private int drolls = 0;

    @NotNull
    @Min(0)
    private int nrolls = 0;

    @NotNull
    @Min(0)
    private int prolls = 0;

    @NotNull
    @Min(0)
    private int quarters = 0;

    @NotNull
    @Min(0)
    private int dimes = 0;

    @NotNull
    @Min(0)
    private int nickels = 0;

    @NotNull
    @Min(0)
    private int pennies = 0;

    public int getTwentiesValue() {
        return getTwenties() * Currency.TWENTIES.value();
    }

    public int getTensValue() {
        return getTens() * Currency.TENS.value();
    }

    public int getFivesValue() {
        return getFives() * Currency.FIVES.value();
    }

    public int getOnesValue() {
        return getOnes() * Currency.ONES.value();
    }

    public int getQrollsValue() {
        return getQrolls() * Currency.QROLLS.value();
    }

    public int getDrollsValue() {
        return getDrolls() * Currency.DROLLS.value();
    }

    public int getNrollsValue() {
        return getNrolls() * Currency.NROLLS.value();
    }

    public int getProllsValue() {
        return getProlls() * Currency.PROLLS.value();
    }

    public int getQuartersValue() {
        return getQuarters() * Currency.QUARTERS.value();
    }

    public int getDimesValue() {
        return getDimes() * Currency.DIMES.value();
    }

    public int getNickelsValue() {
        return getNickels() * Currency.NICKELS.value();
    }

    public int getPenniesValue() {
        return getPennies() * Currency.PENNIES.value();
    }

    public int getBoxTotal() {
        return getTwentiesValue() +
                getTensValue() +
                getFivesValue() +
                getOnesValue() +
                getQrollsValue() +
                getDrollsValue() +
                getNrollsValue() +
                getProllsValue() +
                getQuartersValue() +
                getDimesValue() +
                getNickelsValue() +
                getPenniesValue();
    }

    public static Box createDefaultBox() {
        return new Box()
                .setTwenties(14)
                .setTens(11)
                .setFives(11)
                .setOnes(38)
                .setQrolls(0)
                .setNrolls(0)
                .setDrolls(0)
                .setProlls(0)
                .setQuarters(40)
                .setDimes(50)
                .setNickels(30)
                .setPennies(50);
    }

    public void incrementTo(int newTotal) {
        if (newTotal < getBoxTotal()) return;

        while (getBoxTotal() + Currency.TWENTIES.value() <= newTotal) twenties += 1;
        while (getBoxTotal() + Currency.TENS.value() <= newTotal) tens += 1;
        while (getBoxTotal() + Currency.FIVES.value() <= newTotal) fives += 1;
        while (getBoxTotal() + Currency.ONES.value() <= newTotal) ones += 1;
        while (getBoxTotal() + Currency.QUARTERS.value() <= newTotal) quarters += 1;
        while (getBoxTotal() + Currency.DIMES.value() <= newTotal) dimes += 1;
        while (getBoxTotal() + Currency.NICKELS.value() <= newTotal) nickels += 1;
        while (getBoxTotal() + Currency.PENNIES.value() <= newTotal) pennies += 1;
    }

    public void decrementTo(int newTotal) {
        if (newTotal < 0) return;

        if (getBoxTotal() > newTotal) {
            while (getBoxTotal() - Currency.TWENTIES.value() >= newTotal && getTwenties() > 0) twenties--;
            while (getBoxTotal() - Currency.TENS.value() >= newTotal && getTens() > 0) {
                tens--;
                if (getTens() == 0 && getBoxTotal() - Currency.TENS.value() >= newTotal && getTwenties() > 0) {
                    twenties--;
                    tens += 2;
                }
            }
            while (getBoxTotal() - Currency.FIVES.value() >= newTotal && getFives() > 0) {
                fives--;
                if (getFives() == 0 && getBoxTotal() - Currency.FIVES.value() >= newTotal && getTens() > 0) {
                    tens--;
                    fives += 2;
                }
            }
            while (getBoxTotal() - Currency.ONES.value() >= newTotal && getOnes() > 0) {
                ones--;
                if (getOnes() == 0 && getBoxTotal() - Currency.ONES.value() >= newTotal && getFives() > 0) {
                    fives--;
                    ones += 5;
                }
            }
            while (getBoxTotal() - Currency.QUARTERS.value() >= newTotal && getQuarters() > 0) {
                quarters--;
                if (getQuarters() == 0 && getBoxTotal() - Currency.QUARTERS.value() >= newTotal && getOnes() > 0) {
                    ones--;
                    quarters += 4;
                }
            }
            while (getBoxTotal() - Currency.DIMES.value() >= newTotal && getDimes() > 0) {
                dimes--;
                if (getDimes() == 0 && getBoxTotal() - Currency.DIMES.value() >= newTotal && getQuarters() > 0) {
                    quarters--;
                    dimes += 2;
                    nickels += 1;
                }
            }
            while (getBoxTotal() - Currency.NICKELS.value() >= newTotal && getNickels() > 0) {
                nickels--;
                if (getNickels() == 0 && getBoxTotal() - Currency.NICKELS.value() >= newTotal && getDimes() > 0) {
                    dimes--;
                    nickels += 2;
                }
            }
            while (getBoxTotal() - Currency.PENNIES.value() >= newTotal && getPennies() > 0) {
                pennies--;
                if (getPennies() == 0 && getBoxTotal() - Currency.PENNIES.value() >= newTotal && getNickels() > 0) {
                    nickels--;
                    pennies += 5;
                }
            }
        }

    }

    public void updateBox(int newTotal) {
        if (newTotal < 0) return;

        incrementTo(newTotal);
        decrementTo(newTotal);

        if (getBoxTotal() > newTotal) {
            if (getTwenties() > 0) {
                twenties--;
                updateBox(newTotal);
            } else if (getTens() > 0) {
                tens--;
                updateBox(newTotal);
            } else if (getFives() > 0) {
                fives--;
                updateBox(newTotal);
            } else if (getOnes() > 0) {
                ones--;
                updateBox(newTotal);
            } else if (getQuarters() > 0) {
                quarters--;
                updateBox(newTotal);
            } else if (getDimes() > 0) {
                dimes--;
                updateBox(newTotal);
            }
        }
    }

    public void makeChange(Box currentBox, Box idealBox, int total) {
        setTwenties(Math.max(idealBox.getTwenties() - currentBox.getTwenties(), 0));
        setTens(Math.max(idealBox.getTens() - currentBox.getTens(), 0));
        setFives(Math.max(idealBox.getFives() - currentBox.getFives(), 0));
        setOnes(Math.max(idealBox.getOnes() - currentBox.getOnes(), 0));
        setQrolls(Math.max(idealBox.getQrolls() - currentBox.getQrolls(), 0));
        setDrolls(Math.max(idealBox.getDrolls() - currentBox.getDrolls(), 0));
        setNrolls(Math.max(idealBox.getNrolls() - currentBox.getNrolls(), 0));
        setProlls(Math.max(idealBox.getProlls() - currentBox.getProlls(), 0));
        setQuarters(Math.max(idealBox.getQuarters() - currentBox.getQuarters(), 0));
        setDimes(Math.max(idealBox.getDimes() - currentBox.getDimes(), 0));
        setNickels(Math.max(idealBox.getNickels() - currentBox.getNickels(), 0));
        setPennies(Math.max(idealBox.getPennies() - currentBox.getPennies(), 0));

        updateBox(total);

        // cascade extra coins up to denomination above
        while (getPennies() >= 5) {
            pennies -= 5;
            nickels++;
        }
        while (getDimes() >= 3) {
            dimes -= 3;
            quarters++;
            nickels++;
        }
        while (getNickels() >= 5) {
            nickels -= 5;
            quarters++;
        }
        while (getNickels() >= 2) {
            nickels -= 2;
            dimes++;
        }
        while (getQuarters() >= 4) {
            quarters -= 4;
            ones++;
        }

        // check if coin rolls are needed
        if (getQuarters() + currentBox.getQuarters() < idealBox.getQuarters()) {
            if (getTens() > 0) {
                tens--;
                qrolls++;
            } else if (getTwenties() > 0) {
                twenties--;
                tens++;
                qrolls++;
            }
        }
        if (getDimes() + currentBox.getDimes() < idealBox.getDimes()) {
            if (getFives() > 0) {
                fives--;
                drolls++;
            } else if (getTens() > 0) {
                tens--;
                fives++;
                drolls++;
            }
        }
        if (getNickels() + currentBox.getNickels() < idealBox.getNickels()) {
            if (getOnes() > 10) {
                ones -= 2;
                nrolls++;
            } else if (getFives() > 0) {
                fives--;
                ones += 3;
                nrolls++;
            }
        }
        if (getPennies() + currentBox.getPennies() < idealBox.getPennies()) {
            if (getOnes() > 10) {
                ones--;
                prolls += 2;
            } else if (getFives() > 0) {
                fives--;
                ones += 4;
                prolls += 2;
            }
        }
    }

    public void resetAll() {
        setTwenties(0);
        setTens(0);
        setFives(0);
        setOnes(0);
        setQrolls(0);
        setDrolls(0);
        setNrolls(0);
        setProlls(0);
        setQuarters(0);
        setDimes(0);
        setNickels(0);
        setPennies(0);
    }

    public void addChange(Box changeBox) {
        setTwenties(getTwenties() + changeBox.getTwenties());
        setTens(getTens() + changeBox.getTens());
        setFives(getFives() + changeBox.getFives());
        setOnes(getOnes() + changeBox.getOnes());
        setQrolls(getQrolls() + changeBox.getQrolls());
        setDrolls(getDrolls() + changeBox.getDrolls());
        setNrolls(getNrolls() + changeBox.getNrolls());
        setProlls(getProlls() + changeBox.getProlls());
        setQuarters(getQuarters() + changeBox.getQuarters());
        setDimes(getDimes() + changeBox.getDimes());
        setNickels(getNickels() + changeBox.getNickels());
        setPennies(getPennies() + changeBox.getPennies());
    }
}
