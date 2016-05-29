/*
 * #!
 * Ontopia Rest
 * #-
 * Copyright (C) 2001 - 2016 The Ontopia Project
 * #-
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * !#
 */

package net.ontopia.topicmaps.rest.resources;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.ontopia.topicmaps.rest.utils.HeaderUtils;
import org.apache.commons.collections4.IteratorUtils;
import org.restlet.representation.Representation;
import org.restlet.representation.Variant;

public class AbstractPagedResource extends AbstractOntopiaResource {

	protected boolean paging = true;
	protected int offset = 0; // todo: default options
	protected int limit = 100; // todo: default options
	
	public long getOffset() {
		return offset;
	}

	public long getLimit() {
		return limit;
	}

	public boolean isPaging() {
		return paging;
	}

	public void setPaging(boolean paging) {
		this.paging = paging;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Representation toRepresentation(Object source, Variant target) throws IOException {
		boolean pageable = isPageable(source, target);
		if (pageable) {
			source = page((Collection) source);
		}
		return super.toRepresentation(source, target);
	}

	protected boolean isPageable(Object source, Variant target) {
		return paging && (source instanceof Collection);
	}
	
	protected <C> Iterator<C> page(Collection<C> collection) {
		
		offset = getIntegerFromQuery(Parameters.OFFSET.getName(), 0); // todo: default options
		limit = getIntegerFromQuery(Parameters.LIMIT.getName(), 100); // todo: default options

		addPagingHeaders(collection.size(), offset, limit);
		
		int to = offset + limit;

		// shorcuts where possible
		// todo: not possible at the moment, but we might be able to create a new
		// QueryCollection with a query that has limit and offset in it.
//		if (collection instanceof QueryCollection) {
//			QueryCollection qc = (QueryCollection) collection;
//			
//		}

		// java optimized List.subList
		if (collection instanceof List) {
			try {
				return ((List<C>) collection).subList(offset, Math.min(collection.size(), to)).iterator();
			} catch (IndexOutOfBoundsException ioobe) {
				// offset > size
				return Collections.emptyIterator();
			}
		}

		// fallback to bounded iterator
		try {
			return IteratorUtils.boundedIterator(collection.iterator(), offset, limit);
		} catch (IllegalArgumentException iae) {
			// offset of limit negative
			return Collections.emptyIterator();
		}
	}

	protected void addPagingHeaders(int size, int offset, int limit) {
		HeaderUtils.addResponseHeader(getResponse(), "X-Paging-Count", Integer.toString(size));
		HeaderUtils.addResponseHeader(getResponse(), "X-Paging-Limit", Integer.toString(limit));
		HeaderUtils.addResponseHeader(getResponse(), "X-Paging-Offset", Integer.toString(offset));
		HeaderUtils.addResponseHeader(getResponse(), "X-Paging", offset + "-" + (Math.min(offset + limit, size) - 1) + "/" + size);
	}
}
