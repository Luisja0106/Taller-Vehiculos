import type { ChangeEvent } from "react";

interface filter {
	label: string;
	name: string;
}

type useFiltersParams = {
	filtersRaw: filter[];
	onSearch: (f: Record<string, FormDataEntryValue | null>) => void;
};

export function useFilters({ onSearch, filtersRaw }: useFiltersParams) {
	const handleSubmit = (event: ChangeEvent<HTMLFormElement>) => {
		event.preventDefault();

		const formData = new FormData(event.currentTarget);

		const filters = Object.fromEntries(
			filtersRaw.map((f) => [f.label, formData.get(f.name)]),
		);
		onSearch(filters);
	};

	return {
		handleSubmit,
	};
}
