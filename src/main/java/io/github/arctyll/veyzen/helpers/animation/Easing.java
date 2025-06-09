/*
 * Copyright (c) 2022 LaVache-FR
 * GPL-3.0 license
 */

package io.github.arctyll.veyzen.helpers.animation;

public interface Easing {

    Easing LINEAR = new Easing() {
		@Override
		public float ease(float t, float b, float c, float d) {
			return c * t / d + b;
		}
	};

	Easing QUAD_IN = new Easing() {
		@Override
		public float ease(float t, float b, float c, float d) {
			t /= d;
			return c * t * t + b;
		}
	};

	Easing QUAD_OUT = new Easing() {
		@Override
		public float ease(float t, float b, float c, float d) {
			t /= d;
			return -c * t * (t - 2) + b;
		}
	};

	Easing QUAD_IN_OUT = new Easing() {
		@Override
		public float ease(float t, float b, float c, float d) {
			t /= d / 2;
			if (t < 1) return c / 2 * t * t + b;
			t--;
			return -c / 2 * (t * (t - 2) - 1) + b;
		}
	};

	Easing CUBIC_IN = new Easing() {
		@Override
		public float ease(float t, float b, float c, float d) {
			t /= d;
			return c * t * t * t + b;
		}
	};

	Easing CUBIC_OUT = new Easing() {
		@Override
		public float ease(float t, float b, float c, float d) {
			t = t / d - 1;
			return c * (t * t * t + 1) + b;
		}
	};

	Easing CUBIC_IN_OUT = new Easing() {
		@Override
		public float ease(float t, float b, float c, float d) {
			t /= d / 2;
			if (t < 1) return c / 2 * t * t * t + b;
			t -= 2;
			return c / 2 * (t * t * t + 2) + b;
		}
	};

	Easing QUARTIC_IN = new Easing() {
		@Override
		public float ease(float t, float b, float c, float d) {
			t /= d;
			return c * t * t * t * t + b;
		}
	};

	Easing QUARTIC_OUT = new Easing() {
		@Override
		public float ease(float t, float b, float c, float d) {
			t = t / d - 1;
			return -c * (t * t * t * t - 1) + b;
		}
	};

	Easing QUARTIC_IN_OUT = new Easing() {
		@Override
		public float ease(float t, float b, float c, float d) {
			t /= d / 2;
			if (t < 1) return c / 2 * t * t * t * t + b;
			t -= 2;
			return -c / 2 * (t * t * t * t - 2) + b;
		}
	};

	Easing QUINTIC_IN = new Easing() {
		@Override
		public float ease(float t, float b, float c, float d) {
			t /= d;
			return c * t * t * t * t * t + b;
		}
	};

	Easing QUINTIC_OUT = new Easing() {
		@Override
		public float ease(float t, float b, float c, float d) {
			t = t / d - 1;
			return c * (t * t * t * t * t + 1) + b;
		}
	};

	Easing QUINTIC_IN_OUT = new Easing() {
		@Override
		public float ease(float t, float b, float c, float d) {
			t /= d / 2;
			if (t < 1) return c / 2 * t * t * t * t * t + b;
			t -= 2;
			return c / 2 * (t * t * t * t * t + 2) + b;
		}
	};

	Easing SINE_IN = new Easing() {
		@Override
		public float ease(float t, float b, float c, float d) {
			return -c * (float) Math.cos(t / d * (Math.PI / 2)) + c + b;
		}
	};

	Easing SINE_OUT = new Easing() {
		@Override
		public float ease(float t, float b, float c, float d) {
			return c * (float) Math.sin(t / d * (Math.PI / 2)) + b;
		}
	};

	Easing SINE_IN_OUT = new Easing() {
		@Override
		public float ease(float t, float b, float c, float d) {
			return -c / 2 * ((float) Math.cos(Math.PI * t / d) - 1) + b;
		}
	};

	Easing EXPO_IN = new Easing() {
		@Override
		public float ease(float t, float b, float c, float d) {
			return (t == 0) ? b : c * (float) Math.pow(2, 10 * (t / d - 1)) + b;
		}
	};

	Easing EXPO_OUT = new Easing() {
		@Override
		public float ease(float t, float b, float c, float d) {
			return (t == d) ? b + c : c * (-(float) Math.pow(2, -10 * t / d) + 1) + b;
		}
	};

	Easing EXPO_IN_OUT = new Easing() {
		@Override
		public float ease(float t, float b, float c, float d) {
			if (t == 0) return b;
			if (t == d) return b + c;
			t /= d / 2;
			if (t < 1) return c / 2 * (float) Math.pow(2, 10 * (t - 1)) + b;
			t--;
			return c / 2 * (-(float) Math.pow(2, -10 * t) + 2) + b;
		}
	};

	Easing CIRC_IN = new Easing() {
		@Override
		public float ease(float t, float b, float c, float d) {
			t /= d;
			return -c * ((float) Math.sqrt(1 - t * t) - 1) + b;
		}
	};

	Easing CIRC_OUT = new Easing() {
		@Override
		public float ease(float t, float b, float c, float d) {
			t = t / d - 1;
			return c * (float) Math.sqrt(1 - t * t) + b;
		}
	};

	Easing CIRC_IN_OUT = new Easing() {
		@Override
		public float ease(float t, float b, float c, float d) {
			t /= d / 2;
			if (t < 1) return -c / 2 * ((float) Math.sqrt(1 - t * t) - 1) + b;
			t -= 2;
			return c / 2 * ((float) Math.sqrt(1 - t * t) + 1) + b;
		}
	};

	Easing BOUNCE_OUT = new Easing() {
		@Override
		public float ease(float t, float b, float c, float d) {
			t /= d;
			if (t < (1 / 2.75f)) {
				return c * (7.5625f * t * t) + b;
			} else if (t < (2 / 2.75f)) {
				t -= (1.5f / 2.75f);
				return c * (7.5625f * t * t + .75f) + b;
			} else if (t < (2.5f / 2.75f)) {
				t -= (2.25f / 2.75f);
				return c * (7.5625f * t * t + .9375f) + b;
			} else {
				t -= (2.625f / 2.75f);
				return c * (7.5625f * t * t + .984375f) + b;
			}
		}
	};

	Easing BOUNCE_IN = new Easing() {
		@Override
		public float ease(float t, float b, float c, float d) {
			return c - BOUNCE_OUT.ease(d - t, 0, c, d) + b;
		}
	};

	Easing BOUNCE_IN_OUT = new Easing() {
		@Override
		public float ease(float t, float b, float c, float d) {
			if (t < d / 2) return BOUNCE_IN.ease(t * 2, 0, c, d) * 0.5f + b;
			return BOUNCE_OUT.ease(t * 2 - d, 0, c, d) * 0.5f + c * 0.5f + b;
		}
	};

    /**
     * The basic function for easing.
     *
     * @param t the time (either frames or in seconds/milliseconds)
     * @param b the beginning value
     * @param c the value changed
     * @param d the duration time
     * @return the eased value
     */
    float ease(float t, float b, float c, float d);

    /**
     * A base class for elastic easings.
     */
    abstract class Elastic implements Easing {
        private float amplitude;
        private float period;

        /**
         * Creates a new Elastic easing with the specified settings.
         *
         * @param amplitude the amplitude for the elastic function
         * @param period    the period for the elastic function
         */
        public Elastic(float amplitude, float period) {
            this.amplitude = amplitude;
            this.period = period;
        }

        /**
         * Creates a new Elastic easing with default settings (-1f, 0f).
         */
        public Elastic() {
            this(-1f, 0f);
        }

        /**
         * Returns the period.
         *
         * @return the period for this easing
         */
        public float getPeriod() {
            return period;
        }

        /**
         * Sets the period to the given value.
         *
         * @param period the new period
         */
        public void setPeriod(float period) {
            this.period = period;
        }

        /**
         * Returns the amplitude.
         *
         * @return the amplitude for this easing
         */
        public float getAmplitude() {
            return amplitude;
        }

        /**
         * Sets the amplitude to the given value.
         *
         * @param amplitude the new amplitude
         */
        public void setAmplitude(float amplitude) {
            this.amplitude = amplitude;
        }
    }

    /**
     * An Elastic easing used for ElasticIn functions.
     */
    class ElasticIn extends Elastic {
        public ElasticIn(float amplitude, float period) {
            super(amplitude, period);
        }

        public ElasticIn() {
            super();
        }

        public float ease(float t, float b, float c, float d) {
            float a = getAmplitude();
            float p = getPeriod();
            if (t == 0) return b;
            if ((t /= d) == 1) return b + c;
            if (p == 0) p = d * .3f;
            float s = 0;
            if (a < Math.abs(c)) {
                a = c;
                s = p / 4;
            }
            else s = p / (float) (2 * Math.PI) * (float) Math.asin(c / a);
            return -(a * (float) Math.pow(2, 10 * (t -= 1)) * (float) Math.sin((t * d - s) * (2 * Math.PI) / p)) + b;
        }
    }

    /**
     * An Elastic easing used for ElasticOut functions.
     */
    class ElasticOut extends Elastic {
        public ElasticOut(float amplitude, float period) {
            super(amplitude, period);
        }

        public ElasticOut() {
            super();
        }

        public float ease(float t, float b, float c, float d) {
            float a = getAmplitude();
            float p = getPeriod();
            if (t == 0) return b;
            if ((t /= d) == 1) return b + c;
            if (p == 0) p = d * .3f;
            float s = 0;
            if (a < Math.abs(c)) {
                a = c;
                s = p / 4;
            }
            else s = p / (float) (2 * Math.PI) * (float) Math.asin(c / a);
            return a * (float) Math.pow(2, -10 * t) * (float) Math.sin((t * d - s) * (2 * Math.PI) / p) + c + b;
        }
    }

    /**
     * An Elastic easing used for ElasticInOut functions.
     */
    class ElasticInOut extends Elastic {
        public ElasticInOut(float amplitude, float period) {
            super(amplitude, period);
        }

        public ElasticInOut() {
            super();
        }

        public float ease(float t, float b, float c, float d) {
            float a = getAmplitude();
            float p = getPeriod();
            if (t == 0) return b;
            if ((t /= d / 2) == 2) return b + c;
            if (p == 0) p = d * (.3f * 1.5f);
            float s = 0;
            if (a < Math.abs(c)) {
                a = c;
                s = p / 4f;
            }
            else s = p / (float) (2 * Math.PI) * (float) Math.asin(c / a);
            if (t < 1)
                return -.5f * (a * (float) Math.pow(2, 10 * (t -= 1)) * (float) Math.sin((t * d - s) * (2 * Math.PI) / p)) + b;
            return a * (float) Math.pow(2, -10 * (t -= 1)) * (float) Math.sin((t * d - s) * (2 * Math.PI) / p) * .5f + c + b;
        }
    }

    /**
     * A base class for Back easings.
     */
    abstract class Back implements Easing {
        /**
         * The default overshoot is 10% (1.70158).
         */
        public static final float DEFAULT_OVERSHOOT = 1.70158f;

        private float overshoot;

        /**
         * Creates a new Back instance with the default overshoot (1.70158).
         */
        public Back() {
            this(DEFAULT_OVERSHOOT);
        }

        /**
         * Creates a new Back instance with the specified overshoot.
         *
         * @param overshoot the amount to overshoot by -- higher number
         *                  means more overshoot and an overshoot of 0 results in
         *                  cubic easing with no overshoot
         */
        public Back(float overshoot) {
            this.overshoot = overshoot;
        }

        /**
         * Returns the overshoot for this easing.
         *
         * @return this easing's overshoot
         */
        public float getOvershoot() {
            return overshoot;
        }

        /**
         * Sets the overshoot to the given value.
         *
         * @param overshoot the new overshoot
         */
        public void setOvershoot(float overshoot) {
            this.overshoot = overshoot;
        }
    }

    /////////// BOUNCE EASING: exponentially decaying parabolic bounce  //////////////

    /**
     * Back easing in - backtracking slightly, then reversing direction and moving to target.
     */
    class BackIn extends Back {
        public BackIn() {
            super();
        }

        public BackIn(float overshoot) {
            super(overshoot);
        }

        public float ease(float t, float b, float c, float d) {
            float s = getOvershoot();
            return c * (t /= d) * t * ((s + 1) * t - s) + b;
        }
    }

    /**
     * Back easing out - moving towards target, overshooting it slightly, then reversing and coming back to target.
     */
    class BackOut extends Back {
        public BackOut() {
            super();
        }

        public BackOut(float overshoot) {
            super(overshoot);
        }

        public float ease(float t, float b, float c, float d) {
            float s = getOvershoot();
            return c * ((t = t / d - 1) * t * ((s + 1) * t + s) + 1) + b;
        }
    }

    /**
     * Back easing in/out - backtracking slightly, then reversing direction and moving to target,
     * then overshooting target, reversing, and finally coming back to target.
     */
    class BackInOut extends Back {
        public BackInOut() {
            super();
        }

        public BackInOut(float overshoot) {
            super(overshoot);
        }

        public float ease(float t, float b, float c, float d) {
            float s = getOvershoot();
            if ((t /= d / 2) < 1) return c / 2 * (t * t * (((s *= (1.525)) + 1) * t - s)) + b;
            return c / 2 * ((t -= 2) * t * (((s *= (1.525)) + 1) * t + s) + 2) + b;
        }
    }
}
