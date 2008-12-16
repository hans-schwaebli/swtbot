/*******************************************************************************
 * Copyright (c) 2008 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Generates a combination of the specified elements.
 *
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
class CombinationGenerator<T> implements Iterable<List<T>> {

	private final int			r;
	private final T[]			values;
	private ArrayList<List<T>>	result;

	/**
	 * @param r the number of elements in the combination
	 * @param values the values that should be combined.
	 */
	CombinationGenerator(int r, T... values) {
		this.r = r;
		this.values = values;
		initialize();
	}

	private void initialize() {
		result = new ArrayList<List<T>>();
		for (int i = 1; i <= r; i++) {
			FixedSizeCombinationGenerator<T> combinationGenerator = new FixedSizeCombinationGenerator<T>(i, values);
			for (List<T> list : combinationGenerator) {
				result.add(list);
			}
		}
	}

	public Iterator<List<T>> iterator() {
		return result.iterator();
	}

	class FixedSizeCombinationGenerator<E> implements Iterator<List<E>>, Iterable<List<E>> {
		private int[]		a;
		private int			n;
		private int			r;
		private BigInteger	numLeft;
		private BigInteger	total;
		private final E[]	elements;

		FixedSizeCombinationGenerator(int r, E... elements) {
			this.elements = elements;
			int n = elements.length;
			if (r > n) {
				throw new IllegalArgumentException();
			}
			if (n < 1) {
				throw new IllegalArgumentException();
			}
			this.n = n;
			this.r = r;
			a = new int[r];
			BigInteger nFact = getFactorial(n);
			BigInteger rFact = getFactorial(r);
			BigInteger nminusrFact = getFactorial(n - r);
			total = nFact.divide(rFact.multiply(nminusrFact));
			reset();
		}

		public void reset() {
			for (int i = 0; i < a.length; i++) {
				a[i] = i;
			}
			numLeft = new BigInteger(total.toString());
		}

		public BigInteger getNumLeft() {
			return numLeft;
		}

		public BigInteger getTotal() {
			return total;
		}

		private BigInteger getFactorial(int n) {
			BigInteger fact = BigInteger.ONE;
			for (int i = n; i > 1; i--) {
				fact = fact.multiply(new BigInteger(Integer.toString(i)));
			}
			return fact;
		}

		private int[] getIndices() {
			if (numLeft.equals(total)) {
				numLeft = numLeft.subtract(BigInteger.ONE);
				return a;
			}

			int i = r - 1;
			while (a[i] == n - r + i) {
				i--;
			}
			a[i] = a[i] + 1;
			for (int j = i + 1; j < r; j++) {
				a[j] = a[i] + j - i;
			}
			numLeft = numLeft.subtract(BigInteger.ONE);
			return a;
		}

		public boolean hasNext() {
			return numLeft.compareTo(BigInteger.ZERO) == 1;
		}

		public List<E> next() {
			ArrayList<E> arrayList = new ArrayList<E>();
			int[] indices = getIndices();
			for (int i = 0; i < indices.length; i++) {
				arrayList.add(elements[indices[i]]);
			}
			return arrayList;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

		public Iterator<List<E>> iterator() {
			return this;
		}
	}

}
