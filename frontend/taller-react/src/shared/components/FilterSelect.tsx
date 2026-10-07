import type { ChangeEvent } from "react";

interface Option {
	value: string | number;
	name: string;
}

interface FilterSelectProps {
	id: string;
	label: string;
	options: Option[];
	value?: string | number;
	onChange?: (e: ChangeEvent<HTMLSelectElement>) => void;
	placeHolder?: string;
}

export function FilterSelect({
	id,
	label,
	options,
	value,
	onChange,
	placeHolder,
}: FilterSelectProps) {
	return (
		<div className="flex flex-col gap-1">
			<label htmlFor={id} className="font-medium text-gray-500 text-xs">
				{label}
			</label>
			<select
				id={id}
				value={value}
				onChange={onChange}
				className="cursor-pointer rounded-md border border-slate-300/50 border-solid bg-white px-4 py-2 text-sm outline-none focus:border-blue-500"
			>
				<option value="" disabled>
					{placeHolder}
				</option>
				{options.map((option) => (
					<option value={option.value} key={option.value}>
						{option.name}
					</option>
				))}
			</select>
		</div>
	);
}
