package com.k2.Wiget.testAdapters;

import com.k2.Adapter.Adapter;
import com.k2.Adapter.annotation.Adapts;
import com.k2.Wiget.testTypes.TypeA;
import com.k2.Wiget.testTypes.TypeB;
import com.k2.Wiget.testTypes.TypeC;

@Adapts
public class AdaptC2A implements Adapter<TypeC, TypeA> {

	@Override public Class<TypeC> adaptFrom() { return TypeC.class; }
 	@Override public Class<TypeA> adaptTo() { return TypeA.class; }
 	@Override public TypeA adapt(TypeC from) { return new Adaption(from); }
 	@Override public Class<? extends TypeA> getAdapter() { return Adaption.class; }

 	private class Adaption extends TypeA  {
		
		private TypeC from;
		
		Adaption(TypeC from) {
			super(0, null, null);
			this.from = from;
		}

		@Override
		public int getNumber() { return 0; }

		@Override
		public String getAlias() { return from.getAlias(); }

		@Override
		public String getTitle() { return from.getDescription(); }
		
	}


}
